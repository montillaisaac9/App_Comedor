package com.example.app_comedor.data.db.converter

import androidx.room.TypeConverter
import com.example.app_comedor.data.network.models.auth.UserDTO.CareerDTO

class CareerTypeConverter {
    @TypeConverter
    fun fromCareerList(careers: List<CareerDTO>): String {
        return kotlinx.serialization.json.Json.encodeToString(kotlinx.serialization.builtins.ListSerializer(CareerDTO.serializer()), careers)
    }

    @TypeConverter
    fun toCareerList(data: String): List<CareerDTO> {
        return kotlinx.serialization.json.Json.decodeFromString(kotlinx.serialization.builtins.ListSerializer(CareerDTO.serializer()), data)
    }
}
