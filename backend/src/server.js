require('dotenv').config();
const express = require('express');
const cors = require('cors');
const bcrypt = require('bcryptjs');
const mongoose = require('mongoose');
const { User, Contact } = require('./models');

const app = express();
app.use(cors());
app.use(express.json());

mongoose.connect(process.env.MONGODB_URI);

app.post('/login', async (req, res) => {
  const { accountNumber, password } = req.body;
  const user = await User.findOne({ accountNumber });
  if (!user) return res.status(401).json({ success: false, message: 'Usuario no encontrado' });

  const ok = await bcrypt.compare(password, user.passwordHash);
  if (!ok) return res.status(401).json({ success: false, message: 'Contraseña inválida' });

  res.json({ success: true, user });
});

app.get('/saldo/:id', async (req, res) => {
  const user = await User.findOne({ accountNumber: req.params.id });
  if (!user) return res.status(404).json({ message: 'Usuario no encontrado' });

  res.json({
    accountNumber: user.accountNumber,
    currentBalance: user.currentBalance,
    creditBalance: user.creditBalance,
    creditLimit: user.creditLimit,
    availableCredit: user.creditLimit - user.creditBalance
  });
});

app.get('/contactos/:id', async (req, res) => {
  const contacts = await Contact.find({ ownerAccount: req.params.id });
  res.json(contacts);
});

app.post('/contactos', async (req, res) => {
  const contact = await Contact.create(req.body);
  res.status(201).json(contact);
});

app.post('/transferir', async (req, res) => {
  const { fromAccount, toAccount, amount } = req.body;
  const from = await User.findOne({ accountNumber: fromAccount });
  const to = await User.findOne({ accountNumber: toAccount });

  if (!from || !to) return res.status(404).json({ success: false, message: 'Cuenta no encontrada' });
  if (amount <= 0) return res.status(400).json({ success: false, message: 'Monto inválido' });
  if (from.currentBalance < amount) return res.status(400).json({ success: false, message: 'Fondos insuficientes' });

  from.currentBalance -= amount;
  to.currentBalance += amount;

  await from.save();
  await to.save();

  res.json({ success: true, message: 'Transferencia completada' });
});

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`BankApp API running on ${port}`));
