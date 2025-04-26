# 🎯 PokeData

**PokeData** is a modern Android application built with Jetpack Compose that allows users to discover and explore detailed information about Pokémon. It emphasizes clean architecture, reactive state management, and seamless user experience.


![PokeData Banner](https://github.com/user-attachments/assets/a36a7054-ab96-45bd-9414-c33737d5e547)

---

## 📱 Overview

PokeData is designed with a strong focus on modern Android development practices. It showcases the use of reactive UI with **Jetpack Compose**, **MVI architecture**, and **Redux-like state management**. The app also supports **dynamic theming** and **localization**, making it accessible and customizable for all users.
[Download APK](https://drive.google.com/file/d/14ecRboXzgKmclk_0Nqq2IanAAsdUjJqq/view?usp=sharing)

---

## 🚀 Features

- 🔎 Search Pokémon by name
- 📄 View detailed Pokémon info (abilities, description)
- 🌐 Real-time data from [PokeAPI](https://pokeapi.co/)
- 🌓 Switch between Light, Dark, and System themes
- 🌍 Multilingual support (English, Indonesian)
- ✨ Smooth animations using Lottie

---

## 🧩 Tech Stack

| Category              | Technology                                       |
|----------------------|--------------------------------------------------|
| UI                   | Jetpack Compose, Material 3                      |
| Navigation           | [Voyager](https://github.com/adrielcafe/voyager)|
| Architecture         | MVI + Redux-style state management              |
| Networking           | Retrofit                                         |
| Data Storage         | Room, DataStore                                  |
| Pagination           | Paging3                                          |
| Dependency Injection | Koin                                             |
| Animations           | Lottie                                           |

---

## 🛠️ Setup

To get started:

1. Clone the repository:
   ```bash
   git clone https://github.com/tiofani03/pokedata.git
   ```
2. Open in **Android Studio**
3. Run the app on a device/emulator

---

## 🌐 Localization

The app uses a JSON-based localization system.

Supported languages:
- English (`strings_en.json`)
- Bahasa Indonesia (`strings_id.json`)

To add new languages:
1. Create `strings_xx.json` in `assets/` folder
2. Add key-value pairs:
   ```json
   {
     "home": "Beranda",
     "search": "Cari"
   }
   ```

---

## 🎨 Theme Management

Users can choose:
- ☀️ Light Mode
- 🌙 Dark Mode
- ⚙️ Follow System

Preferences are stored with **DataStore** and applied app-wide.

---

## 📸 Screenshots

| Splash | Login | Register |
|------|---------|----------|
| ![](https://github.com/user-attachments/assets/fe0688dc-49a7-4797-b8c4-34077cea436b) | ![](https://github.com/user-attachments/assets/ebae6059-a8ea-4720-a6d8-0ab55146cdf4) | ![](https://github.com/user-attachments/assets/f831e4d3-f943-45db-ac47-70a31b257dad) |


| Home | Detail | Settings |
|------|---------|----------|
| ![](https://github.com/user-attachments/assets/96161602-851a-4439-9da1-4f2261e19dec) | ![](https://github.com/user-attachments/assets/a2171bf2-9154-4c42-8867-ae814ae8acec) | ![](https://github.com/user-attachments/assets/5844aa7f-b460-4ddd-b9b2-702a00c67bb9) |

---

# 🎨 Design Reference

The UI design of PokeData is inspired by:
- Pokémon brand colors and minimal UI
- Pokémon App Design [Dribble](https://dribbble.com/shots/16833947-Mobile-Pokedex-App-Design-Exploration)
- Material You + Jetpack Compose guidelines from material.io
- Edge-to-edge and dark/light theme transitions based on Google Design
- All icons and illustrations follow proper licensing and attribution.


## 🤝 Contribution

Contributions are welcome!

1. Fork the repo
2. Create a branch:
   ```bash
   git checkout -b feature/your-feature
   ```
3. Commit and push your changes
4. Open a Pull Request ✨

---

Enjoy building and exploring the world of Pokémon with **PokeData**!  
If you have questions or suggestions, feel free to open an issue.

