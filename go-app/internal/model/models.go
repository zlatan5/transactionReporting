package model

import "time"

type Account struct {
	ID             uint   `gorm:"primaryKey;column:account_id" json:"account_id"`
	DocumentNumber string `gorm:"uniqueIndex;column:document_number;not null" json:"document_number"`
}

type OperationType struct {
	ID          uint   `gorm:"primaryKey;column:operation_type_id" json:"operation_type_id"`
	Description string `gorm:"column:description;not null" json:"description"`
}

type Transaction struct {
	ID              uint          `gorm:"primaryKey;column:transaction_id" json:"transaction_id"`
	AccountID       uint          `gorm:"column:account_id;not null" json:"account_id"`
	OperationTypeID uint          `gorm:"column:operation_type_id;not null" json:"operation_type_id"`
	Amount          float64       `gorm:"column:amount;not null" json:"amount"`
	EventDate       time.Time     `gorm:"column:event_date;autoCreateTime" json:"event_date"`

	Account       Account       `gorm:"foreignKey:AccountID;references:ID" json:"-"`
	OperationType OperationType `gorm:"foreignKey:OperationTypeID;references:ID" json:"-"`
}

func (Account) TableName() string { return "accounts" }
func (OperationType) TableName() string { return "operation_types" }
func (Transaction) TableName() string { return "transactions" }
