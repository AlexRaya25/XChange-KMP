# XChange

XChange is a currency conversion application developed with **Kotlin Multiplatform (KMP)**. It is currently available for **Android** and **Desktop**.

## 🌐 Web Version

You can access the **XChange Web App** here:   🔗 **[XChange Web](https://alexraya25.github.io/XChange-KMP/)**  


## 📸 Screenshots

### 📱 Mobile Version Images

<img src="https://github.com/user-attachments/assets/47e27a1e-5849-4141-ae5d-d24f62f1f2b8" width="150" />
<img src="https://github.com/user-attachments/assets/efc66550-23ff-426f-93b3-2f2a511b09f5" width="150" />

### 💻 Desktop Version Images

<img src="https://github.com/user-attachments/assets/1f261057-fcda-49a8-8efe-f2161ef30f17" width="150" />
<img src="https://github.com/user-attachments/assets/44edbfc4-6dd6-4b18-b1f2-96c8ef6faa7d" width="150" />

---


## 🔧 Roadmap

✅ Support for **Android** 📱  
✅ Support for **Desktop** 💻  
⬜ Support for **iOS** 🍏  
✅ Support for **Web** 🌐  

---

## 🚀 Technologies Used

XChange leverages **Kotlin Multiplatform** to share logic across platforms. Here are the main technologies used:

### 📦 Main Dependencies

```kotlin
// Jetpack Compose
implementation(compose.runtime)
implementation(compose.foundation)
implementation(compose.material3)
implementation(compose.ui)
implementation(compose.components.resources)
implementation(compose.components.uiToolingPreview)

// Networking
implementation(libs.ktor.client.core)
implementation(libs.ktor.client.json)
implementation(libs.ktor.serialization.kotlinx.json)
implementation(libs.ktor.client.serialization)
implementation(libs.ktor.client.logging)
implementation(libs.ktor.client.cio)
implementation(libs.ktor.client.content.negotiation)

// Dependency Injection
implementation(libs.koin.core)

// AndroidX Lifecycle
implementation(libs.androidx.lifecycle.viewmodel)
implementation(libs.androidx.lifecycle.runtime.compose)

// Kotlin Standard Library
implementation(libs.kotlin.stdlib.jdk8)
```

---

## 📊 Charts

- **Android**: [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
  
  ```kotlin
  implementation("com.github.PhilJay:MPAndroidChart:$mpandroidchartVersion")
  ```

- **Desktop**: [KoalaPlot](https://github.com/KoalaPlot/koalaplot-core)
  
  ```kotlin
  implementation("io.github.koalaplot:koalaplot-core:$koalaplotCoreVersion")
  ```

---

## 🌍 API Used

XChange retrieves currency conversion data from the free **Frankfurter** API. 📡

Example API call:

```bash
https://api.frankfurter.dev/latest?from=USD&to=EUR
```

---

## ⚙️ Installation and Execution

### Clone the Repository

```bash
git clone https://github.com/AlexRaya25/xchange.git
cd xchange
```

### Android

```bash
./gradlew installDebug
```

### Desktop

```bash
./gradlew run
```

### iOS (Future)

```bash
./gradlew iosDeploy
```

---
