package db

import (
	"GoAndroid-PetApi/pkg/common/config"
	"fmt"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func InitDb(config *config.Config) (*gorm.DB, error) {
	dsn := fmt.Sprintf(
		"host=%s port=%s user=%s password=%s dbname=%s sslmode=%s",
		config.DBHost, config.DBPort, config.DBUser, config.DBPassword, config.DBName, config.SSLMode,
	)

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})

	if err != nil {
		return db, err
	}

	return db, nil
}
