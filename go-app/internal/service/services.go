package service

import (
	"errors"
	"math"
	"gorm.io/gorm"
	"github.com/zlatan5/transactionReporting/go-app/internal/model"
)

type Services struct {
	DB *gorm.DB
}

func (s *Services) CreateAccount(document string) (*model.Account, error) {
	var exists int64
	s.DB.Model(&model.Account{}).Where("document_number = ?", document).Count(&exists)
	if exists > 0 { return nil, errors.New("account already exists") }
	acc := &model.Account{DocumentNumber: document}
	return acc, s.DB.Create(acc).Error
}

func (s *Services) GetAccount(id uint) (*model.Account, error) {
	var acc model.Account
	if err := s.DB.First(&acc, id).Error; err != nil { return nil, err }
	return &acc, nil
}

func signAmount(operationTypeID uint, amount float64) float64 {
	abs := math.Abs(amount)
	switch operationTypeID {
	case 1,2,3:
		return -abs
	case 4:
		return abs
	default:
		return amount
	}
}

func (s *Services) CreateTransaction(accountID, operationTypeID uint, amount float64) (*model.Transaction, error) {
	if err := s.DB.First(&model.Account{}, accountID).Error; err != nil { return nil, err }
	if err := s.DB.First(&model.OperationType{}, operationTypeID).Error; err != nil { return nil, err }
	trx := &model.Transaction{AccountID: accountID, OperationTypeID: operationTypeID, Amount: signAmount(operationTypeID, amount)}
	return trx, s.DB.Create(trx).Error
}
