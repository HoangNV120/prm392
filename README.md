### Các bước thực hiện của External Storage 

## 1. MainActivity.java – xử lý logic tạo thư mục & quyền
   🧩 Biến và khai báo
   private static final int STORAGE_PERMISSION_CODE = 100;
   private static final String TAG = "PERMISSION_TAG";
   private EditText folderNameEt;
   private MaterialButton createFolderBtn;
   Khai báo mã yêu cầu quyền (code 100), tag log, và ánh xạ các thành phần giao diện (ô nhập & nút bấm).

## 📦 onCreate()
folderNameEt = findViewById(R.id.folderNameEt);
createFolderBtn = findViewById(R.id.createFolderBtn);
Liên kết biến Java với giao diện (ID trong XML).

createFolderBtn.setOnClickListener(new View.OnClickListener() {
...
});
Khi nhấn nút "Create Folder", sẽ:

Kiểm tra đã có quyền chưa qua checkPermission()

Nếu có: gọi createFolder()

Nếu chưa: gọi requestPermission() để xin quyền

## 📂 createFolder()
String folderName = folderNameEt.getText().toString().trim();
File file = new File(Environment.getExternalStorageDirectory() + "/" + folderName);
boolean folderCreated = file.mkdir();
Lấy tên thư mục từ người dùng

Tạo đường dẫn trong bộ nhớ ngoài

Tạo thư mục nếu chưa có

Hiển thị thông báo (Toast) thành công/thất bại

## 🔐 checkPermission() – kiểm tra quyền
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
return Environment.isExternalStorageManager();
} else {
...
}
Nếu Android 11 trở lên (API 30+): kiểm tra quyền MANAGE_EXTERNAL_STORAGE

Nếu thấp hơn: kiểm tra quyền READ và WRITE

## 🔐 requestPermission() – xin quyền
# Android 11 trở lên:

Mở trang cài đặt đặc biệt để xin quyền "All files access"

intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
intent.setData(Uri.fromParts("package", this.getPackageName(), null));

# Android 10 trở xuống:

Xin quyền bằng ActivityCompat.requestPermissions() với READ và WRITE

## 🔄 storageActivityResultLauncher – xử lý sau khi xin quyền (Android 11+)
registerForActivityResult(..., new ActivityResultCallback<ActivityResult>() {...})
Sau khi người dùng đồng ý/không đồng ý ở màn hình xin quyền:

Nếu đã cấp: gọi createFolder()

Nếu không: hiện thông báo từ chối

## 🔄 onRequestPermissionsResult() – xử lý sau khi xin quyền (Android 10 trở xuống)
boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;
Nếu cả READ và WRITE đều được cấp: gọi createFolder()

Nếu không: hiển thị Toast từ chối

## 🖼️ activity_main.xml – giao diện
<EditText android:id="@+id/folderNameEt" ... />
<MaterialButton android:id="@+id/createFolderBtn" ... />
Gồm:

Một ô nhập tên thư mục

Một nút để tạo thư mục

## 🛡️ AndroidManifest.xml – khai báo quyền
# 🔧 Permissions

# <!-- Android < 11 -->
## <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
## <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

# <!-- Android >= 11 -->
## <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"
##  tools:ignore="ScopedStorage" />
Cấp quyền tương ứng theo phiên bản

## ⚙️ Application
android:requestLegacyExternalStorage="true"
tools:targetApi="31"
requestLegacyExternalStorage: để các thiết bị Android 10 vẫn dùng cách cũ (không Scoped Storage)

targetApi: để IDE biết bạn dùng đến Android 12 trở xuống

## ✅ Tóm tắt luồng hoạt động
Người dùng nhập tên thư mục ➝ bấm "Create Folder"

App kiểm tra quyền:

Nếu đã có: tạo thư mục ngay

Nếu chưa có: xin quyền

Android 11+: mở trang đặc biệt để xin MANAGE_EXTERNAL_STORAGE

Android thấp hơn: dùng requestPermissions

Sau khi người dùng chọn:

Nếu được cấp: tạo thư mục

Nếu không: hiện thông báo từ chối

