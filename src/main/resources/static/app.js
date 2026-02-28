// BuckPal Frontend Application
const app = {
    // API Base URL
    apiBase: '',

    // Show message to user
    showMessage(message, type = 'info') {
        const messageBox = document.getElementById('messageBox');
        messageBox.textContent = message;
        messageBox.className = `message-box message-${type} show`;

        setTimeout(() => {
            messageBox.classList.remove('show');
        }, 5000);
    },

    // Load all accounts
    async loadAccounts() {
        try {
            const response = await fetch(`${this.apiBase}/accounts`);
            const data = await response.json();

            if (data.success && data.accounts) {
                this.displayAccounts(data.accounts);
            } else {
                this.showMessage(data.errorMessage || 'Failed to load accounts', 'error');
            }
        } catch (error) {
            this.showMessage(`Error loading accounts: ${error.message}`, 'error');
        }
    },

    // Display accounts in the list
    displayAccounts(accounts) {
        const accountsList = document.getElementById('accountsList');

        if (accounts.length === 0) {
            accountsList.innerHTML = '<p class="no-accounts">No accounts found. Create one to get started!</p>';
            return;
        }

        accountsList.innerHTML = accounts.map(account => `
            <div class="account-item">
                <span class="account-id">Account #${account.id}</span>
                <span class="account-balance">Balance: ${this.formatMoney(account.balance)}</span>
            </div>
        `).join('');
    },

    // Format money amount
    formatMoney(amount) {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(amount); // Backend stores amounts as whole units
    },

    // Create Account
    async createAccount(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const initialBalance = formData.get('initialBalance');

        try {
            const response = await fetch(`${this.apiBase}/accounts/create?initialBalance=${initialBalance}`, {
                method: 'POST'
            });
            const data = await response.json();

            if (data.success) {
                this.showMessage(`Account #${data.account.id} created successfully with balance ${this.formatMoney(data.account.balance)}`, 'success');
                form.reset();
                this.loadAccounts(); // Refresh the accounts list
            } else {
                this.showMessage(data.errorMessage || 'Failed to create account', 'error');
            }
        } catch (error) {
            this.showMessage(`Error creating account: ${error.message}`, 'error');
        }
    },

    // Send Money
    async sendMoney(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const sourceAccountId = formData.get('sourceAccountId');
        const targetAccountId = formData.get('targetAccountId');
        const amount = formData.get('sendAmount');

        try {
            const response = await fetch(
                `${this.apiBase}/accounts/send?sourceAccountId=${sourceAccountId}&targetAccountId=${targetAccountId}&amount=${amount}`,
                { method: 'POST' }
            );
            const data = await response.json();

            if (data.success) {
                this.showMessage(
                    `Successfully sent ${this.formatMoney(amount)} from Account #${sourceAccountId} to Account #${targetAccountId}`,
                    'success'
                );
                form.reset();
                this.loadAccounts(); // Refresh the accounts list
            } else {
                this.showMessage(data.errorMessage || 'Failed to send money', 'error');
            }
        } catch (error) {
            this.showMessage(`Error sending money: ${error.message}`, 'error');
        }
    },

    // Deposit Money
    async depositMoney(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const accountId = formData.get('depositAccountId');
        const amount = formData.get('depositAmount');

        try {
            const response = await fetch(
                `${this.apiBase}/accounts/deposit?accountId=${accountId}&amount=${amount}`,
                { method: 'POST' }
            );
            const data = await response.json();

            if (data.success) {
                this.showMessage(
                    `Successfully deposited ${this.formatMoney(amount)} to Account #${accountId}. New balance: ${this.formatMoney(data.account.balance)}`,
                    'success'
                );
                form.reset();
                this.loadAccounts(); // Refresh the accounts list
            } else {
                this.showMessage(data.errorMessage || 'Failed to deposit money', 'error');
            }
        } catch (error) {
            this.showMessage(`Error depositing money: ${error.message}`, 'error');
        }
    },

    // Withdraw Money
    async withdrawMoney(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const accountId = formData.get('withdrawAccountId');
        const amount = formData.get('withdrawAmount');

        try {
            const response = await fetch(
                `${this.apiBase}/accounts/withdraw?accountId=${accountId}&amount=${amount}`,
                { method: 'POST' }
            );
            const data = await response.json();

            if (data.success) {
                this.showMessage(
                    `Successfully withdrew ${this.formatMoney(amount)} from Account #${accountId}. New balance: ${this.formatMoney(data.account.balance)}`,
                    'success'
                );
                form.reset();
                this.loadAccounts(); // Refresh the accounts list
            } else {
                this.showMessage(data.errorMessage || 'Failed to withdraw money', 'error');
            }
        } catch (error) {
            this.showMessage(`Error withdrawing money: ${error.message}`, 'error');
        }
    },

    // Get Account Balance
    async getBalance(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const accountId = formData.get('balanceAccountId');

        try {
            const response = await fetch(`${this.apiBase}/accounts/balance?accountId=${accountId}`);
            const data = await response.json();

            const balanceResult = document.getElementById('balanceResult');

            if (data.success) {
                balanceResult.innerHTML = `
                    <div class="balance-success">
                        <strong>Account #${data.account.id}</strong><br>
                        Balance: <span class="balance-amount">${this.formatMoney(data.account.balance)}</span>
                    </div>
                `;
                balanceResult.className = 'result-box success';
            } else {
                balanceResult.innerHTML = `<div class="balance-error">${data.errorMessage || 'Account not found'}</div>`;
                balanceResult.className = 'result-box error';
            }
        } catch (error) {
            this.showMessage(`Error getting balance: ${error.message}`, 'error');
        }
    },

    // Initialize app
    init() {
        console.log('BuckPal Frontend initialized');
        // Automatically load accounts on page load
        this.loadAccounts();
    }
};

// Initialize app when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    app.init();
});
