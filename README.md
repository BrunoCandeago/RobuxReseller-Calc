# 🛒 Robux Reseller Calculator
> A fast and practical desktop application built for Robux resellers to automate complex fee calculations and manage daily sales smoothly.

## 📌 About The Project

When managing a Robux store on social media, calculating prices manually for every single customer can be slow and prone to errors due to the marketplace taxes. 

This application solves that problem by automating the math. You simply type the amount of Robux, and the calculator instantly tells you exactly how much the Gamepass should be set to, how many "Clean Robux" you will actually receive, and the final fiat price you need to charge your customer based on your specific rates.

## 🚀 Getting Started

**No installation required!**
1. Download the latest `.exe` file from the [Releases](https://github.com/BrunoCandeago/RobuxReseller-Calc/releases) tab.
2. Double-click the file to open the calculator.

## 📖 How to Use

The calculator works in real-time. You can type in any of the three boxes, and the others will calculate automatically:

* **Clean Robux:** Type how many Robux the customer wants to receive.
* **Gamepass Robux:** Type the amount of a Gamepass to see how much clean Robux it will yield.
* **Price:** Type your local currency price to see how many Robux you need to send.

Next to every box, there is a **"Copy"** button so you can instantly paste the exact numbers into your chat with the buyer.

## ⚙️ Managing Your Sellers (Suppliers)

You can save different suppliers or pricing tiers (e.g., "Supplier A", "Supplier B", "Premium Rate") so you don't have to type your rates every time.

Use the buttons at the bottom right:
* **Add:** Create a new supplier. Enter their name and their price per 80 Robux.
* **Edit:** Select a supplier from the dropdown list and click Edit to update their name or their current rate.
* **Delete:** Remove a supplier you no longer use.

*All changes are saved automatically.*

## 🛠️ Built With

* **Java 25**
* **Java Swing** (GUI Framework)
* **FlatLaf** (Modern Cross-Platform Look and Feel)

## 🚀 Roadmap (Version 1.0)

> *This project is currently under active development. Below is the plan for the upcoming releases.*

- [x] Core calculation engine (Gamepass/Clean/Price)
- [x] Input validation and overflow protection
- [x] Dynamic seller dropdown integration
- [x] Add "Copy to Clipboard" buttons
- [x] Modernize UI with FlatLaf
- [x] Implement Full CRUD for Seller Management
- [x] Package release as a standalone `.exe` / `.jar` for Windows

## 🔮 Future Scope (Backlog)

* **Tiered Pricing System:** Implement logic to automatically apply bulk discounts and custom rates based on transaction volume.
