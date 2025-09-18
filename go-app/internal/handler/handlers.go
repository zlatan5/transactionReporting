package handler

import (
	"net/http"
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/zlatan5/transactionReporting/go-app/internal/service"
)

type Handlers struct{ Svc *service.Services }

type accountRequest struct {
	DocumentNumber string `json:"document_number" binding:"required,min=11,max=14"`
}

type transactionRequest struct {
	AccountID       uint    `json:"account_id" binding:"required"`
	OperationTypeID uint    `json:"operation_type_id" binding:"required"`
	Amount          float64 `json:"amount" binding:"required,gt=0"`
}

func (h *Handlers) CreateAccount(c *gin.Context) {
	var req accountRequest
	if err := c.ShouldBindJSON(&req); err != nil { c.JSON(http.StatusBadRequest, gin.H{"success":false,"message":err.Error()}); return }
	acc, err := h.Svc.CreateAccount(req.DocumentNumber)
	if err != nil { c.JSON(http.StatusConflict, gin.H{"success":false,"message":err.Error()}); return }
	c.JSON(http.StatusCreated, gin.H{"success":true, "message":"Account created successfully", "data": acc})
}

func (h *Handlers) GetAccount(c *gin.Context) {
	var id uint
	if _, err := fmt.Sscan(c.Param("id"), &id); err != nil { c.JSON(http.StatusBadRequest, gin.H{"message":"invalid id"}); return }
	acc, err := h.Svc.GetAccount(id)
	if err != nil { c.JSON(http.StatusNotFound, gin.H{"message":"Account not found"}); return }
	c.JSON(http.StatusOK, acc)
}

func (h *Handlers) CreateTransaction(c *gin.Context) {
	var req transactionRequest
	if err := c.ShouldBindJSON(&req); err != nil { c.JSON(http.StatusBadRequest, gin.H{"success":false,"message":err.Error()}); return }
	trx, err := h.Svc.CreateTransaction(req.AccountID, req.OperationTypeID, req.Amount)
	if err != nil { c.JSON(http.StatusNotFound, gin.H{"success":false,"message":err.Error()}); return }
	c.JSON(http.StatusCreated, gin.H{"success":true, "data": gin.H{"message": "Transaction created successfully", "transaction_id": trx.ID}})
}
