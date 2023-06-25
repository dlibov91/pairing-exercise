package io.billie.functional.data

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object Fixtures {
    fun orderRequestJson(): String {
        return """
    {
      "order_id": "d30b102e-3e69-4e33-a3ab-6438f2be3ef6",
      "organization_id": "797fa5e1-b78c-4e3f-b615-60df1a70be6b",
      "customer_email": "example@example.com",
      "total_amount": 12.34
    }
    """.trimIndent()
    }

    fun orgRequestJsonNameBlank(): String {
        return "{\n" +
                "  \"name\": \"\",\n" +
                "  \"date_founded\": \"18/10/1922\",\n" +
                "  \"country_code\": \"GB\",\n" +
                "  \"vat_number\": \"333289454\",\n" +
                "  \"registration_number\": \"3686147\",\n" +
                "  \"legal_entity_type\": \"NONPROFIT_ORGANIZATION\",\n" +
                "  \"contact_details\": {\n" +
                "    \"phone_number\": \"+443700100222\",\n" +
                "    \"fax\": \"\",\n" +
                "    \"email\": \"yourquestions@bbc.co.uk\"\n" +
                "  }\n" +
                "}"
    }

    fun orgRequestJsonNoName(): String {
        return "{\n" +
                "  \"date_founded\": \"18/10/1922\",\n" +
                "  \"country_code\": \"GB\",\n" +
                "  \"vat_number\": \"333289454\",\n" +
                "  \"registration_number\": \"3686147\",\n" +
                "  \"legal_entity_type\": \"NONPROFIT_ORGANIZATION\",\n" +
                "  \"contact_details\": {\n" +
                "    \"phone_number\": \"+443700100222\",\n" +
                "    \"fax\": \"\",\n" +
                "    \"email\": \"yourquestions@bbc.co.uk\"\n" +
                "  }\n" +
                "}"
    }

    fun orgRequestJsonNoLegalEntityType(): String {
        return "{\n" +
                "  \"name\": \"BBC\",\n" +
                "  \"date_founded\": \"18/10/1922\",\n" +
                "  \"country_code\": \"GB\",\n" +
                "  \"vat_number\": \"333289454\",\n" +
                "  \"registration_number\": \"3686147\",\n" +
                "  \"contact_details\": {\n" +
                "    \"phone_number\": \"+443700100222\",\n" +
                "    \"fax\": \"\",\n" +
                "    \"email\": \"yourquestions@bbc.co.uk\"\n" +
                "  }\n" +
                "}"
    }

    fun orgRequestJsonNoContactDetails(): String {
        return "{\n" +
                "  \"name\": \"BBC\",\n" +
                "  \"date_founded\": \"18/10/1922\",\n" +
                "  \"country_code\": \"GB\",\n" +
                "  \"vat_number\": \"333289454\",\n" +
                "  \"registration_number\": \"3686147\",\n" +
                "  \"legal_entity_type\": \"NONPROFIT_ORGANIZATION\"\n" +
                "}"
    }

    fun orgRequestJson(): String {
        return "{\n" +
                "  \"name\": \"BBC\",\n" +
                "  \"date_founded\": \"18/10/1922\",\n" +
                "  \"country_code\": \"GB\",\n" +
                "  \"vat_number\": \"333289454\",\n" +
                "  \"registration_number\": \"3686147\",\n" +
                "  \"legal_entity_type\": \"NONPROFIT_ORGANIZATION\",\n" +
                "  \"contact_details\": {\n" +
                "    \"phone_number\": \"+443700100222\",\n" +
                "    \"fax\": \"\",\n" +
                "    \"email\": \"yourquestions@bbc.co.uk\"\n" +
                "  }\n" +
                "}"
    }

    fun orgRequestJsonCountryCodeBlank(): String {
        return "{\n" +
                "  \"name\": \"BBC\",\n" +
                "  \"date_founded\": \"18/10/1922\",\n" +
                "  \"country_code\": \"\",\n" +
                "  \"vat_number\": \"333289454\",\n" +
                "  \"registration_number\": \"3686147\",\n" +
                "  \"legal_entity_type\": \"NONPROFIT_ORGANIZATION\",\n" +
                "  \"contact_details\": {\n" +
                "    \"phone_number\": \"+443700100222\",\n" +
                "    \"fax\": \"\",\n" +
                "    \"email\": \"yourquestions@bbc.co.uk\"\n" +
                "  }\n" +
                "}"
    }

    fun orgRequestJsonNoCountryCode(): String {
        return "{\n" +
                "  \"name\": \"BBC\",\n" +
                "  \"date_founded\": \"18/10/1922\",\n" +
                "  \"vat_number\": \"333289454\",\n" +
                "  \"registration_number\": \"3686147\",\n" +
                "  \"legal_entity_type\": \"NONPROFIT_ORGANIZATION\",\n" +
                "  \"contact_details\": {\n" +
                "    \"phone_number\": \"+443700100222\",\n" +
                "    \"fax\": \"\",\n" +
                "    \"email\": \"yourquestions@bbc.co.uk\"\n" +
                "  }\n" +
                "}"
    }

    fun orgRequestJsonCountryCodeIncorrect(): String {
        return "{\n" +
                "  \"name\": \"BBC\",\n" +
                "  \"date_founded\": \"18/10/1922\",\n" +
                "  \"country_code\": \"XX\",\n" +
                "  \"vat_number\": \"333289454\",\n" +
                "  \"registration_number\": \"3686147\",\n" +
                "  \"legal_entity_type\": \"NONPROFIT_ORGANIZATION\",\n" +
                "  \"contact_details\": {\n" +
                "    \"phone_number\": \"+443700100222\",\n" +
                "    \"fax\": \"\",\n" +
                "    \"email\": \"yourquestions@bbc.co.uk\"\n" +
                "  }\n" +
                "}"
    }

    fun bbcFixture(id: UUID): Map<String, Any> {
        val data = HashMap<String, Any>()
        data["id"] = id
        data["name"] = "BBC"
        data["date_founded"] = SimpleDateFormat("yyyy-MM-dd").parse("1922-10-18")
        data["country_code"] = "GB"
        data["vat_number"] = "333289454"
        data["registration_number"] = "3686147"
        data["legal_entity_type"] = "NONPROFIT_ORGANIZATION"
        return data
    }

    fun bbcContactFixture(id: UUID): Map<String, Any> {
        val data = HashMap<String, Any>()
        data["id"] = id
        data["phone_number"] = "+443700100222"
        data["fax"] = ""
        data["email"] = "yourquestions@bbc.co.uk"
        return data
    }


}
