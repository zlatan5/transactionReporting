package main

import (
	"log"
	"github.com/zlatan5/transactionReporting/go-app/internal/db"
	"github.com/zlatan5/transactionReporting/go-app/internal/seed"
	"github.com/zlatan5/transactionReporting/go-app/internal/service"
	"github.com/zlatan5/transactionReporting/go-app/internal/handler"
	"github.com/zlatan5/transactionReporting/go-app/internal/router"
)

func main() {
	database := db.ConnectAndMigrate()
	if err := seed.SeedOperationTypes(database); err != nil { log.Fatalf("seed error: %v", err) }
	svc := &service.Services{DB: database}
	h := &handler.Handlers{Svc: svc}
	r := router.SetupRoutes(h)
	if err := r.Run(":8080"); err != nil { log.Fatal(err) }
}
