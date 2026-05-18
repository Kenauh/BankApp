const mongoose = require('mongoose');

const ContactSchema = new mongoose.Schema({
  ownerAccount: { type: String, required: true },
  name: { type: String, required: true },
  bank: { type: String, required: true },
  type: { type: String, default: 'CLABE' },
  destinationAccount: { type: String, required: true }
});

const UserSchema = new mongoose.Schema({
  accountNumber: { type: String, unique: true, required: true },
  name: { type: String, required: true },
  passwordHash: { type: String, required: true },
  currentBalance: { type: Number, default: 0 },
  creditBalance: { type: Number, default: 0 },
  creditLimit: { type: Number, default: 0 }
});

module.exports = {
  User: mongoose.model('User', UserSchema),
  Contact: mongoose.model('Contact', ContactSchema)
};
