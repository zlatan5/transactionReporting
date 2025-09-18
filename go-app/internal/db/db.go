package db

import (
	"log"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"github.com/zlatan5/transactionReporting/go-app/internal/model"
)

func ConnectAndMigrate() *gorm.DB {
	database, err := gorm.Open(sqlite.Open("file:app.db?mode=memory&cache=shared"), &gorm.Config{})
	if err != nil {
		log.Fatalf("failed to connect database: %v", err)
	}
	if err := database.AutoMigrate(&model.Account{}, &model.OperationType{}, &model.Transaction{}); err != nil {
		log.Fatalf("failed to migrate: %v", err)
	}
	return database
}
