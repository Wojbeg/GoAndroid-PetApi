package pets

import (
	"GoAndroid-PetApi/pkg/common/models"
	"net/http"

	"github.com/gofiber/fiber/v2"
)

func (r *Repository) DeletePet(context *fiber.Ctx) error {

	id := context.Params("id")
	if id == "" {
		context.Status(http.StatusInternalServerError).JSON(
			&fiber.Map{"message": "id cannot be empty"},
		)
		return nil
	}

	petModel := models.Pet{}
	err := r.DB.Delete(petModel, id)

	if err.Error != nil {
		context.Status(http.StatusBadRequest).JSON(
			&fiber.Map{"message": "could not delete the pet"},
		)
		return err.Error
	}

	context.Status(http.StatusOK).JSON(
		&fiber.Map{"message": "pet deleted successfully"},
	)

	return nil
}
