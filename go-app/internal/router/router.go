package router

import (
	"github.com/gin-gonic/gin"
	"github.com/zlatan5/transactionReporting/go-app/internal/handler"
)

func SetupRoutes(h *handler.Handlers) *gin.Engine {
	r := gin.Default()
	v1 := r.Group("/api/v1")
	{
		v1.POST("/accounts", h.CreateAccount)
		v1.GET("/accounts/:id", h.GetAccount)
		v1.POST("/transactions", h.CreateTransaction)
	}
	return r
}
