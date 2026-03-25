# GraalVM Installer for Windows OS

<p>
  <img align="left" width="220"
       src="https://github.com/SourceRabbit/GraalVM-Windows-Installer/blob/main/Images/graalvm-installer-logo.png"
       alt="GraalVM Installer for Windows OS">

  Simple and reliable installer for GraalVM Java Development Kit on Windows.<br>
  Automatically downloads the selected GraalVM version and configures
  <code>JAVA_HOME</code> and <code>PATH</code> environment variables.<br><br>

  Designed for developers who want a clean, fast and predictable GraalVM setup,
  without manual configuration or environment issues.

  <p align="right">
    <img src="https://img.shields.io/badge/language-Java-orange" alt="Java"/>
    <img src="https://img.shields.io/badge/platform-Windows-blue" alt="Platform"/>
    <img src="https://img.shields.io/badge/license-GPL%20v3-green" alt="License"/>
    <img src="https://img.shields.io/github/v/release/SourceRabbit/GraalVM-Windows-Installer" alt="Latest Release"/>
  </p>
</p>

<br clear="left"/>


[![](https://dcbadge.vercel.app/api/server/nRKETyjJ7E)](https://discord.gg/nRKETyjJ7E)

## About

**GraalVM Installer for Windows** is a lightweight and reliable installer that simplifies the setup of the **GraalVM Java Development Kit (JDK)** on Windows systems.

It removes the need for manual downloads and environment configuration by automatically downloading the selected GraalVM distribution and configuring the required **system-level** variables (`JAVA_HOME` and `PATH`).

> ⚠️ **Administrator privileges are required** to run the installer, as it modifies system environment variables.

<p align="center">
  <img src="https://github.com/SourceRabbit/GraalVM-Windows-Installer/blob/main/Images/HowToUse.gif" alt="GraalVM Installer for Windows">
</p>

After the installation is completed, the system is immediately ready for development, allowing you to run, build and experiment with GraalVM-based applications without additional setup steps.

For more information about GraalVM and its capabilities, visit:
[https://www.graalvm.org/](https://www.graalvm.org/)

---

## Downloads

Find the latest version on the releases page:<br>
🔗 [https://github.com/SourceRabbit/GraalVM-Windows-Installer/releases](https://github.com/SourceRabbit/GraalVM-Windows-Installer/releases)

---

## Built With

| Technology | Description |
|---|---|
| [Java](https://www.java.com/) | Core application logic and UI |
| [Inno Setup](https://jrsoftware.org/isinfo.php) | Windows installer packaging |
| [Launch4J](https://launch4j.sourceforge.net/) | Wraps the JAR into a native Windows `.exe` |

---

## Project Structure

```
GraalVM-Windows-Installer/
├── NetBeans Project/       # Java source code (NetBeans IDE project)
├── InnoSetup Installer/    # Inno Setup script for packaging the installer
├── Launch4J/               # Launch4J configuration for .exe wrapping
├── Downloads/              # Download configuration files
├── Images/                 # Screenshots and assets used in the README
├── LICENSE                 # GNU General Public License v3
└── README.md
```

---

## Changelog

### v2.0.3 — September 17, 2024
- Downloads manager `HashMap` changed to `LinkedHashMap` to maintain the order of the downloads list
- Installation Wizard is now **signed**

### v2.0.2 — January 22, 2024
- Environment variables are now set at the **System** level (not User level)
- GraalVM Installer now requires **administrator privileges** to run

### v2.0.1 — January 20, 2024
- Application has been **completely rewritten in Java**
- The old C# project is now deprecated

### v1.0.4 — May 29, 2021
- Maintenance release

### v1.0.3 — April 5, 2021
- Minor UI changes

### v1.0.2 — April 4, 2021
- Minor UI changes

### v1.0.1 — April 4, 2021
- First ever public release of the GraalVM Windows Installer 🎉

---

## License

This project is licensed under the **GNU General Public License v3.0**.<br>
See the [LICENSE](LICENSE) file for details.
