CREATE DATABASE J3LP0002
USE  J3LP0002

CREATE TABLE tblRole (
	roleId VARCHAR(5) PRIMARY KEY NOT NULL,
	roleName VARCHAR(5) NOT NULL
)
GO

CREATE TABLE tblStatus (
	statusId INT PRIMARY KEY NOT NULL,
	statusName VARCHAR(10) NOT NULL
)
GO 

CREATE TABLE tblAccount (
	username VARCHAR(50) PRIMARY KEY NOT NULL,
	password varchar(50) NOT NULL,
	fullName NVARCHAR(100) NOT NULL,
	roleId VARCHAR(5) FOREIGN KEY REFERENCES dbo.tblRole(roleId),
	statusId INT DEFAULT 1  FOREIGN KEY REFERENCES tblStatus(statusId)
)
GO 

CREATE TABLE tblProduct (
	productId INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	title NVARCHAR(50) NOT NULL,
	quantity INT NOT NULL,
	image VARBINARY(MAX),
	author NVARCHAR(50) NOT NULL,
	date DATE DEFAULT GETDATE(),
	description NVARCHAR(200) NOT NULL,
	price INT DEFAULT 0 NOT NULL,
	categoryId NVARCHAR(20) FOREIGN KEY REFERENCES tblCategory(categoryId),
	statusId INT DEFAULT 1 FOREIGN KEY REFERENCES tblStatus(statusId)
)
GO

CREATE TABLE tblCategory (
	categoryId NVARCHAR(20) PRIMARY KEY NOT NULL,
	categoryName NVARCHAR(20) NOT NULL
)
GO 

CREATE TABLE tblOrders (
	orderId VARCHAR(36) PRIMARY KEY,
	username VARCHAR(50) FOREIGN KEY REFERENCES dbo.tblAccount(username),
	name NVARCHAR(50),
	address NVARCHAR(200),
	phoneNumber VARCHAR(10),
	total INT NOT NULL,
	orderDate DATETIME DEFAULT GETDATE(),
	paymentId VARCHAR(10) FOREIGN KEY REFERENCES tblPayment(paymentId)
)
GO 

CREATE TABLE tblOrderDetail (
	detailId INT IDENTITY(1,1) PRIMARY KEY,
	orderId VARCHAR(36) FOREIGN KEY REFERENCES tblOrders(orderId),
	productId INT FOREIGN KEY REFERENCES tblProduct(productId),
	quantity INT NOT NULL,
	totalPrice INT NOT NULL
)
GO 

CREATE TABLE tblPayment (
	paymentId VARCHAR(10) PRIMARY KEY NOT NULL,
	paymentName NVARCHAR(50) NOT NULL,
)
GO 

CREATE TABLE tblDiscount (
	discountId INT IDENTITY(1,1) PRIMARY KEY,
	discountPercent INT NOT NULL,
	date DATE DEFAULT GETDATE() NOT NULL,
	status BIT DEFAULT 1 NOT NULL,
)

DROP TABLE dbo.tblRole
DROP TABLE dbo.tblStatus
DROP TABLE dbo.tblAccount
DROP TABLE dbo.tblProduct
DROP TABLE dbo.tblCategory
DROP TABLE dbo.tblOrders
DROP TABLE dbo.tblOrderDetail
DROP TABLE dbo.tblPayment
DROP TABLE dbo.tblDiscount


