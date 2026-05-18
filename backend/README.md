# BankApp Backend (Node.js + Express + MongoDB)

## Requisitos
- Node.js 20+
- MongoDB local

## Pasos
1. Copia variables de entorno:
   ```bash
   cp .env.example .env
   ```
2. Instala dependencias:
   ```bash
   npm install
   ```
3. Carga datos demo:
   ```bash
   npm run seed
   ```
4. Levanta API:
   ```bash
   npm run dev
   ```

Base URL local: `http://localhost:3000`

## Endpoints
- `POST /login`
- `GET /saldo/:id`
- `POST /transferir`
- `GET /contactos/:id`
- `POST /contactos`
