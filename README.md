# 全画面ブラウザ for Kindle Fire

Kindle Fire 8 HD（第10世代）向けの全画面表示ブラウザアプリケーションです。

## 特徴

- **全画面表示**: ステータスバーを非表示にした完全な全画面表示
- **URL入力欄の切替**: ボタン一つでURL入力欄の表示/非表示を切り替え可能
- **基本的なブラウザ機能**:
  - 戻る/進む/更新ボタン
  - URL入力とページ遷移
  - JavaScript対応
  - ズーム機能
  - ページ履歴の保存

## 動作環境

- **対象デバイス**: Kindle Fire 8 HD（第10世代）
- **最小Android SDK**: API 22 (Android 5.1)
- **ターゲットSDK**: API 33 (Android 13)

## ビルド方法

### 必要な環境

1. **JDK**: Java Development Kit 8以上
2. **Android SDK**: Android SDK Platform 33
3. **Gradle**: 7.5以上（gradlewを使用する場合は不要）

### ビルド手順

#### 1. リポジトリのクローン

```bash
git clone https://github.com/yourusername/fullscreen-browser-forkindle.git
cd fullscreen-browser-forkindle
```

#### 2. Gradleビルド

```bash
# Linux/Mac
./gradlew assembleRelease

# Windows
gradlew.bat assembleRelease
```

ビルドが成功すると、APKファイルが以下の場所に生成されます：

```
app/build/outputs/apk/release/app-release-unsigned.apk
```

#### 3. APKへの署名（オプション）

リリース用のAPKには署名が必要です。

```bash
# キーストアの作成（初回のみ）
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias

# APKへの署名
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 -keystore my-release-key.jks app/build/outputs/apk/release/app-release-unsigned.apk my-key-alias

# zipalignの実行
zipalign -v 4 app/build/outputs/apk/release/app-release-unsigned.apk app/build/outputs/apk/release/fullscreen-browser.apk
```

### Android Studioでのビルド

1. Android Studioを起動
2. "Open an Existing Project"を選択
3. クローンしたディレクトリを選択
4. メニューから`Build > Build Bundle(s) / APK(s) > Build APK(s)`を選択
5. ビルド完了後、通知からAPKの場所を確認

## インストール方法

### Kindle FireへのAPKインストール

1. **開発者オプションを有効化**:
   - 設定 > デバイスオプション > シリアル番号を7回タップ

2. **ADBデバッグを有効化**:
   - 設定 > デバイスオプション > 開発者オプション > ADBを有効化

3. **不明なアプリのインストールを許可**:
   - 設定 > セキュリティとプライバシー > 不明ソースからのアプリをオン

4. **ADBでインストール**:
   ```bash
   adb install app/build/outputs/apk/release/fullscreen-browser.apk
   ```

または、APKファイルをKindle Fireに転送してファイルマネージャーからインストールすることもできます。

## 使い方

1. アプリを起動すると、デフォルトでGoogleが表示されます
2. URL入力欄にアクセスしたいWebサイトのURLを入力
3. 「移動」ボタンまたはキーボードのEnterキーでページを開く
4. 右端の「▼」ボタンでURL入力欄を非表示にして全画面表示
5. 全画面表示中に画面をタップすると「▲」ボタンが表示され、再度URL入力欄を表示可能

### ナビゲーション

- **戻る**: 前のページに戻る
- **進む**: 次のページに進む
- **更新**: 現在のページを再読み込み
- **URL欄切替**: URL入力欄の表示/非表示を切り替え

## 開発

### プロジェクト構成

```
fullscreen-browser-forkindle/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/kindle/fullscreenbrowser/
│   │       │   └── MainActivity.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   ├── values/
│   │       │   │   ├── strings.xml
│   │       │   │   ├── colors.xml
│   │       │   │   └── themes.xml
│   │       │   └── drawable/
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

### カスタマイズ

#### デフォルトURLの変更

`app/src/main/res/values/strings.xml`の`default_url`を編集：

```xml
<string name="default_url">https://example.com</string>
```

#### ユーザーエージェントの変更

デスクトップサイトを表示したい場合、`MainActivity.java`の以下のコメントを解除：

```java
webSettings.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
```

## ライセンス

MIT License

## 貢献

プルリクエストを歓迎します。大きな変更を加える場合は、まずissueを開いて変更内容について議論してください。

## 問題報告

バグ報告や機能要望は、GitHubのIssuesセクションで行ってください。
