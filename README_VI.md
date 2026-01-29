# 🛍️ Hệ Thống Quản Lý Bán Lẻ Toàn Diện

**Tác giả:** Nguyễn Tuấn Vũ (2123110231)  
**Loại Dự Án:** Final Assignment - Full-Stack Web Application  
**Ngôn Ngữ:** Vietnamese

---

## 📋 Mục Lục
- [Tổng Quan Dự Án](#-tổng-quan-dự-án)
- [Công Nghệ Sử Dụng](#-công-nghệ-sử-dụng)
- [Cấu Trúc Dự Án](#-cấu-trúc-dự-án)
- [Cài Đặt Và Chạy](#-cài-đặt-và-chạy)
- [API Endpoints](#-api-endpoints)
- [Tính Năng Chính](#-tính-năng-chính)
- [Hướng Dẫn Sử Dụng](#-hướng-dẫn-sử-dụng)
- [Cấu Hình Database](#-cấu-hình-database)

---

## 🎯 Tổng Quan Dự Án

Đây là một ứng dụng web toàn diện dành cho quản lý bán lẻ, cho phép:
- **Người dùng thường**: Duyệt sản phẩm, thêm vào giỏ hàng, thanh toán, quản lý đơn hàng
- **Người quản trị**: Quản lý sản phẩm, danh mục, người dùng, đơn hàng và kho hàng
- **Hỗ trợ AI**: Tính năng chat AI tích hợp Gemini API để hỗ trợ khách hàng

**Kiến Trúc:** Client-Server  
**Frontend:** React 19  
**Backend:** Spring Boot 3.5.10  
**Database:** MySQL / PostgreSQL  

---

## 🛠️ Công Nghệ Sử Dụng

### Backend (Java)
| Công Nghệ | Phiên Bản | Mục Đích |
|-----------|-----------|---------|
| Spring Boot | 3.5.10 | Framework chính |
| Java | 21 LTS | Ngôn ngữ lập trình |
| Spring Data JPA | Latest | ORM - Quản lý Database |
| Spring Security | Latest | Xác thực & Phân quyền |
| JWT (JJWT) | 0.11.5 | Token-based authentication |
| MySQL Connector | Latest | Driver MySQL |
| PostgreSQL | Latest | Hỗ trợ PostgreSQL |
| Spring Validation | Latest | Xác thực dữ liệu |
| Spring HATEOAS | Latest | RESTful API |

### Frontend (React)
| Công Nghệ | Phiên Bản | Mục Đích |
|-----------|-----------|---------|
| React | 19.2.3 | UI Framework |
| React Router | 7.11.0 | Định tuyến |
| Ant Design | 6.2.0 | UI Components |
| Axios | 1.13.2 | HTTP Client |
| Bootstrap | 5.3.8 | CSS Framework |
| Recharts | 3.7.0 | Biểu đồ Dashboard |
| Styled Components | 6.3.8 | CSS-in-JS |
| Dayjs | 1.11.19 | Xử lý ngày giờ |
| JWT Decode | 4.0.0 | Giải mã JWT token |
| React QR Code | 2.0.18 | QR code generation |
| Password Strength Bar | 0.4.1 | Kiểm tra độ mạnh mật khẩu |

### Công Cụ
- **Build Tool:** Maven
- **Version Control:** Git
- **IDE Khuyến Nghị:** IntelliJ IDEA, VS Code
- **Node.js:** v16+ (cho Frontend)

---

## 📁 Cấu Trúc Dự Án

```
NguyenTuanVu_2123110231_FinalAssignment/
│
├── client/                          # Frontend (React)
│   ├── public/
│   │   ├── index.html
│   │   └── manifest.json
│   ├── src/
│   │   ├── api/                     # API calls
│   │   │   ├── aiApi.js             # Gemini AI API
│   │   │   ├── authApi.js           # Xác thực
│   │   │   ├── axiosClient.js       # Axios configuration
│   │   │   ├── categoryApi.js       # Danh mục
│   │   │   ├── orderApi.js          # Đơn hàng
│   │   │   └── productApi.js        # Sản phẩm
│   │   ├── admin/                   # Admin Dashboard
│   │   │   ├── AdminDashboard.jsx
│   │   │   ├── AdminLayout.jsx
│   │   │   ├── CategoryManage.jsx
│   │   │   ├── ProductManage.jsx
│   │   │   ├── UserManage.jsx
│   │   │   ├── OrderManage.jsx
│   │   │   ├── InventoryManage.jsx
│   │   │   └── Sidebar.jsx
│   │   ├── pages/                   # User Pages
│   │   │   ├── HomePage.jsx
│   │   │   ├── LoginPage.jsx
│   │   │   ├── RegisterPage.jsx
│   │   │   ├── ProductDetail.jsx
│   │   │   ├── CartPage.jsx
│   │   │   ├── CheckoutPage.jsx
│   │   │   ├── ProfilePage.jsx
│   │   │   └── ForbiddenPage.jsx
│   │   ├── components/
│   │   │   ├── AdminRoute.jsx       # Route bảo vệ
│   │   │   └── AIChatBox.jsx        # Chat AI
│   │   ├── App.js
│   │   └── index.js
│   ├── package.json
│   └── README.md
│
├── src/                             # Backend (Java/Spring Boot)
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java # Entry point
│   │   │   ├── config/              # Spring configs
│   │   │   │   ├── CorsConfig.java
│   │   │   │   ├── JwtConfig.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── AiChatController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── CartController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── InventoryController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── UserController.java
│   │   │   ├── entity/              # JPA Entities
│   │   │   │   ├── BaseEntity.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── CartItem.java
│   │   │   │   ├── Role.java
│   │   │   │   └── OrderStatus.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── repository/          # Spring Data JPA Repositories
│   │   │   ├── service/             # Business Logic
│   │   │   └── security/            # JWT, Security
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/                        # Unit Tests
│       └── java/com/example/demo/
│
├── pom.xml                          # Maven configuration
├── mvnw                             # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                         # Maven Wrapper (Windows)
├── README.md
└── README_VI.md                     # File này
```

---

## 🚀 Cài Đặt Và Chạy

### Yêu Cầu Hệ Thống
- **Java:** JDK 21 LTS
- **Node.js:** v16+
- **Maven:** 3.6+
- **MySQL:** 8.0+ hoặc PostgreSQL 12+
- **Git:** v2.0+

### Bước 1: Clone Repository
```bash
git clone https://github.com/your-repo/NguyenTuanVu_2123110231_FinalAssignment.git
cd NguyenTuanVu_2123110231_FinalAssignment
```

### Bước 2: Cài Đặt Database
```sql
-- Tạo database
CREATE DATABASE dbjava CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Hoặc sử dụng PostgreSQL
CREATE DATABASE dbjava;
```

### Bước 3: Cấu Hình Backend
Chỉnh sửa file [src/main/resources/application.properties](src/main/resources/application.properties):

```properties
# Database MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/dbjava?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hoặc PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/dbjava
spring.datasource.username=postgres
spring.datasource.password=your_password

# AI API Key (Gemini)
gemini.api.key=YOUR_GEMINI_API_KEY
```

### Bước 4: Chạy Backend
```bash
# Terminal 1: Chạy Spring Boot
mvn spring-boot:run

# Hoặc build jar
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```
Backend sẽ chạy tại: `http://localhost:8080`

### Bước 5: Chạy Frontend
```bash
# Terminal 2: Cài đặt dependencies
cd client
npm install

# Chạy development server
npm start
```
Frontend sẽ chạy tại: `http://localhost:3000`

### Bước 6: Build Production
```bash
# Frontend
cd client
npm run build

# Backend (đã build ở bước 4)
```

---

## 🔌 API Endpoints

### 🔐 Authentication Endpoints
```
POST   /api/auth/register      - Đăng ký tài khoản
POST   /api/auth/login         - Đăng nhập
POST   /api/auth/logout        - Đăng xuất
POST   /api/auth/refresh       - Làm mới token
GET    /api/auth/verify        - Xác thực token
```

### 👥 User Endpoints
```
GET    /api/users              - Lấy danh sách người dùng (Admin)
GET    /api/users/{id}         - Lấy thông tin người dùng
PUT    /api/users/{id}         - Cập nhật thông tin người dùng
DELETE /api/users/{id}         - Xóa người dùng (Admin)
GET    /api/users/profile      - Lấy profile người dùng hiện tại
```

### 🏷️ Category Endpoints
```
GET    /api/categories         - Lấy tất cả danh mục
GET    /api/categories/{id}    - Lấy danh mục theo ID
POST   /api/categories         - Tạo danh mục (Admin)
PUT    /api/categories/{id}    - Cập nhật danh mục (Admin)
DELETE /api/categories/{id}    - Xóa danh mục (Admin)
```

### 📦 Product Endpoints
```
GET    /api/products           - Lấy danh sách sản phẩm (có phân trang)
GET    /api/products/{id}      - Lấy chi tiết sản phẩm
GET    /api/products/search    - Tìm kiếm sản phẩm
POST   /api/products           - Tạo sản phẩm (Admin)
PUT    /api/products/{id}      - Cập nhật sản phẩm (Admin)
DELETE /api/products/{id}      - Xóa sản phẩm (Admin)
```

### 🛒 Cart Endpoints
```
GET    /api/cart               - Lấy giỏ hàng
POST   /api/cart/items         - Thêm sản phẩm vào giỏ
PUT    /api/cart/items/{id}    - Cập nhật số lượng
DELETE /api/cart/items/{id}    - Xóa sản phẩm khỏi giỏ
DELETE /api/cart/clear         - Xóa tất cả giỏ hàng
```

### 📋 Order Endpoints
```
GET    /api/orders             - Lấy danh sách đơn hàng
GET    /api/orders/{id}        - Lấy chi tiết đơn hàng
POST   /api/orders             - Tạo đơn hàng mới
PUT    /api/orders/{id}        - Cập nhật trạng thái đơn hàng (Admin)
DELETE /api/orders/{id}        - Hủy đơn hàng
```

### 📊 Inventory Endpoints
```
GET    /api/inventory          - Lấy thông tin kho hàng
PUT    /api/inventory/{id}     - Cập nhật tồn kho (Admin)
```

### 🤖 AI Chat Endpoints
```
POST   /api/ai-chat            - Gửi tin nhắn tới AI
GET    /api/ai-chat/history    - Lấy lịch sử chat
DELETE /api/ai-chat/history    - Xóa lịch sử chat
```

---

## ✨ Tính Năng Chính

### 👤 Người Dùng Thường
- ✅ Đăng ký / Đăng nhập với xác thực JWT
- ✅ Duyệt sản phẩm theo danh mục
- ✅ Tìm kiếm sản phẩm
- ✅ Xem chi tiết sản phẩm
- ✅ Thêm/Xoá sản phẩm vào giỏ hàng
- ✅ Thanh toán và tạo đơn hàng
- ✅ Xem lịch sử đơn hàng
- ✅ Quản lý hồ sơ cá nhân
- ✅ Chat AI để hỏi đáp (Powered by Gemini)
- ✅ Tạo QR Code đơn hàng

### 🔧 Admin
- ✅ Dashboard thống kê bán hàng
- ✅ Quản lý danh mục (CRUD)
- ✅ Quản lý sản phẩm (CRUD)
- ✅ Quản lý kho hàng
- ✅ Quản lý đơn hàng (xem/cập nhật trạng thái)
- ✅ Quản lý người dùng
- ✅ Biểu đồ thống kê

### 🔒 Bảo Mật
- ✅ Spring Security với JWT tokens
- ✅ Password encryption (BCrypt)
- ✅ CORS configuration
- ✅ Role-based access control (RBAC)
- ✅ Input validation
- ✅ SQL injection prevention

---

## 📖 Hướng Dẫn Sử Dụng

### Đăng Ký & Đăng Nhập
1. Vào trang chủ `http://localhost:3000`
2. Nhấn "Đăng Ký" để tạo tài khoản mới
3. Nhập email, mật khẩu (phải đạt độ mạnh tối thiểu)
4. Xác nhận đăng ký
5. Đăng nhập bằng email và mật khẩu

### Mua Sắm (User)
1. Duyệt sản phẩm trên trang chủ
2. Nhấn vào sản phẩm để xem chi tiết
3. Chọn số lượng và thêm vào giỏ hàng
4. Vào trang giỏ hàng để kiểm tra
5. Nhấn "Thanh toán" để tạo đơn hàng
6. Xem trạng thái đơn hàng trong "Đơn Hàng Của Tôi"

### Quản Trị (Admin)
1. Đăng nhập với tài khoản Admin
2. Vào "Admin Dashboard" từ menu
3. Sử dụng sidebar để:
   - Quản lý danh mục sản phẩm
   - Quản lý sản phẩm (thêm/sửa/xóa)
   - Xem và cập nhật đơn hàng
   - Quản lý người dùng
   - Xem thống kê bán hàng

### Chat AI
- Bất kỳ lúc nào, nhấn nút "Chat AI" ở góc phải màn hình
- Đặt câu hỏi về sản phẩm, cách sử dụng, v.v.
- AI sẽ trả lời dựa trên nội dung được huấn luyện

---

## 🗄️ Cấu Hình Database

### Schema MySQL/PostgreSQL

```sql
-- Users Table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    phone VARCHAR(20),
    address TEXT,
    role VARCHAR(20),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Categories Table
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Products Table
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    quantity INT,
    category_id BIGINT,
    image_url VARCHAR(500),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Orders Table
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    total_price DECIMAL(10, 2),
    status VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Order Items Table
CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    price DECIMAL(10, 2),
    created_at TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Cart Table
CREATE TABLE carts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNIQUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Cart Items Table
CREATE TABLE cart_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cart_id BIGINT,
    product_id BIGINT,
    quantity INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### Tự Động Tạo Schema
Dự án sử dụng Hibernate để tự động tạo schema:
```properties
spring.jpa.hibernate.ddl-auto=update  # Tự động cập nhật schema
# Hoặc: create (xóa và tạo mới), validate (chỉ kiểm tra)
```

---

## 🧪 Testing

### Unit Tests
```bash
# Chạy tất cả tests
mvn test

# Chạy test cụ thể
mvn test -Dtest=ProductServiceTest
```

### Frontend Tests
```bash
cd client
npm test
```

---

## 📝 Ghi Chú Quan Trọng

1. **API Key Gemini:** Cần lấy API key từ [Google Cloud Console](https://console.cloud.google.com)
2. **Database:** Đảm bảo MySQL/PostgreSQL đang chạy trước khi khởi động ứng dụng
3. **CORS:** Backend đã cấu hình CORS cho `http://localhost:3000`
4. **JWT Token:** Token có thời gian hết hạn, sử dụng `/api/auth/refresh` để làm mới
5. **Mật Khẩu:** Phải chứa chữ hoa, chữ thường, số và ký tự đặc biệt

---

## 🐛 Khắc Phục Sự Cố

| Vấn Đề | Giải Pháp |
|--------|----------|
| **Port 8080 đang sử dụng** | Thay đổi port trong `application.properties`: `server.port=8081` |
| **Port 3000 đang sử dụng** | Chạy: `PORT=3001 npm start` |
| **Database connection failed** | Kiểm tra connection string, username, password |
| **JWT token invalid** | Đăng nhập lại để lấy token mới |
| **Gemini API not working** | Kiểm tra API key và quota |
| **Dependency issues** | Chạy: `mvn clean install` hoặc `npm install --legacy-peer-deps` |

---

## 📊 Metrics & Performance

- **Encoding:** UTF-8
- **Timezone:** UTC (Server)
- **API Response Time:** < 200ms
- **Database Queries:** Optimized with JPA
- **Frontend Bundle Size:** ~500KB gzipped

---

## 📞 Liên Hệ & Hỗ Trợ

**Tác Giả:** Nguyễn Tuấn Vũ  
**MSSV:** 2123110231  

---

## 📄 License

Dự án này được tạo cho mục đích học tập.

---

**Cập nhật lần cuối:** 29/01/2026  
**Phiên Bản:** 1.0.0
