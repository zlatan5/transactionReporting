package seed

import (
	"gorm.io/gorm"
	"github.com/zlatan5/transactionReporting/go-app/internal/model"
)

func SeedOperationTypes(db *gorm.DB) error {
	var count int64
	db.Model(&model.OperationType{}).Count(&count)
	if count > 0 { return nil }
	ops := []model.OperationType{
		{ID: 1, Description: "CASH PURCHASE"},
		{ID: 2, Description: "INSTALLMENT PURCHASE"},
		{ID: 3, Description: "WITHDRAWAL"},
		{ID: 4, Description: "PAYMENT"},
	}
	return db.Create(&ops).Error
}
