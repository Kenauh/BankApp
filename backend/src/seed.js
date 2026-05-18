require('dotenv').config();
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const { User, Contact } = require('./models');

(async () => {
  await mongoose.connect(process.env.MONGODB_URI);
  await User.deleteMany({});
  await Contact.deleteMany({});

  const hash = await bcrypt.hash('123456', 10);

  await User.insertMany([
    { accountNumber: '0000009328', name: 'Jonathan', passwordHash: hash, currentBalance: 311.16, creditBalance: 681.06, creditLimit: 900 },
    { accountNumber: '0000008908', name: 'Mamá', passwordHash: hash, currentBalance: 1200, creditBalance: 100, creditLimit: 1500 },
    { accountNumber: '0000009611', name: 'Mari', passwordHash: hash, currentBalance: 830, creditBalance: 50, creditLimit: 500 }
  ]);

  await Contact.insertMany([
    { ownerAccount: '0000009328', name: 'Mamá', bank: 'AZTECA', type: 'CLABE', destinationAccount: '0000008908' },
    { ownerAccount: '0000009328', name: 'Mari', bank: 'BBVA MEXICO', type: 'Débito', destinationAccount: '0000009611' }
  ]);

  console.log('Seed completado. Usuario demo: 0000009328 / 123456');
  process.exit(0);
})();
