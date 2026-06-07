package com.example.yourdigitalpath.presentation.service_request

import androidx.annotation.StringRes
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.ui.theme.AppStrings

data class ServiceConfig(
    val serviceType: ServiceTypes,
    val basePrice: Double,
    val availableTypes: List<String>,
    val dataFields: List<DataField>,
    val requiredFiles: List<FileRequirement>
)

data class DataField(
    val id: String,
    @StringRes val labelRes: Int,
    @StringRes val placeholderRes: Int = 0,
    val type: FieldType = FieldType.TEXT,
    val validation: ValidationType = ValidationType.NONE,
    @StringRes val sectionRes: Int = R.string.data_label,
    val isRequired: (String) -> Boolean = { true },
    /** لو الـ id هو "applicant_relation"، بيحدد أي قايمة صفة تظهر */
    val relationshipType: RelationshipType = RelationshipType.NONE
)

enum class FieldType {
    TEXT, DATE, DROPDOWN, NUMBER, PHONE
}

enum class ValidationType {
    NONE, ARABIC_NAME, NATIONAL_ID, PHONE, NUMERIC
}

/**
 * أنواع الصفة حسب الخدمة:
 * - GENERAL         → صاحب الوثيقة / ولي الأمر / وكيل
 * - MARRIAGE        → الزوج / الزوجة / وكيل
 * - DEATH           → قريب / وكيل قانوني (صاحب الوثيقة مش منطقي)
 * - DIVORCE         → الزوج / الزوجة / وكيل
 * - NONE            → مش خانة صفة
 */
enum class RelationshipType {
    NONE,
    GENERAL,    // شهادة ميلاد — صاحب الوثيقة / ولي الأمر / وكيل
    MARRIAGE,   // زواج — الزوج / الزوجة / وكيل
    DEATH,      // وفاة — ابن/ابنة / أخ/أخت / زوج/زوجة / وكيل
    DIVORCE     // طلاق — الزوج / الزوجة / وكيل
}

data class FileRequirement(
    val id: String,
    @StringRes val labelRes: Int,
    @StringRes val descriptionRes: Int = 0,
    val minCount: Int = 1,
    val maxCount: Int = 1,
    val isRequired: (String) -> Boolean = { true }
)

object ServiceConfigs {
    private val EGYPT_GOVERNORATES = listOf(
        "القاهرة", "الجيزة", "الإسكندرية", "الدقهلية", "البحر الأحمر", "البحيرة", "الفيوم",
        "الغربية", "الإسماعيلية", "المنوفية", "المنيا", "القليوبية", "الوادي الجديد", "السويس",
        "الشرقية", "أسوان", "أسيوط", "بني سويف", "بورسعيد", "دمياط", "جنوب سيناء",
        "كفر الشيخ", "مطروح", "الأقصر", "قنا", "شمال سيناء", "سوهاج"
    )

    val configs = mapOf(
        ServiceTypes.BIRTH_CERTIFICATE to ServiceConfig(
            serviceType = ServiceTypes.BIRTH_CERTIFICATE,
            basePrice = 20.0,
            availableTypes = listOf(
                AppStrings.FULL_COPY,
                AppStrings.SHORT_COPY,
                AppStrings.CERTIFIED_DIGITAL,
                AppStrings.LOST_REPLACEMENT
            ),
            dataFields = listOf(
                DataField(
                    "owner_name",
                    R.string.owner_name_label,
                    R.string.full_name_arabic_placeholder,
                    sectionRes = R.string.document_owner_data,
                    validation = ValidationType.ARABIC_NAME
                ),
                DataField(
                    "owner_dob",
                    R.string.birth_date,
                    type = FieldType.DATE,
                    sectionRes = R.string.document_owner_data
                ),
                DataField(
                    "owner_gov",
                    R.string.birth_governorate,
                    type = FieldType.DROPDOWN,
                    sectionRes = R.string.document_owner_data
                ),
                DataField(
                    "applicant_id",
                    R.string.applicant_id_label_alt,
                    validation = ValidationType.NATIONAL_ID,
                    sectionRes = R.string.applicant_data
                ),
                DataField(
                    "applicant_phone",
                    R.string.phone_number,
                    type = FieldType.PHONE,
                    validation = ValidationType.PHONE,
                    sectionRes = R.string.applicant_data
                )
            ),
            requiredFiles = listOf(
                FileRequirement(
                    "applicant_id_img",
                    R.string.applicant_id_img_label,
                    minCount = 2,
                    maxCount = 2
                ),
                FileRequirement(
                    "old_cert",
                    R.string.old_birth_certificate,
                    isRequired = { type -> type != AppStrings.LOST_REPLACEMENT }
                ),
                FileRequirement(
                    "police_report",
                    R.string.police_report_label,
                    descriptionRes = R.string.police_report_notice,
                    isRequired = { type -> type == AppStrings.LOST_REPLACEMENT }
                ),
            )
        ),

        ServiceTypes.NATIONAL_ID to ServiceConfig(
            serviceType = ServiceTypes.NATIONAL_ID,
            basePrice = 35.0,
            availableTypes = listOf(
                AppStrings.ISSUANCE,
                AppStrings.RENEWAL,
                AppStrings.LOST_REPLACEMENT,
                AppStrings.DAMAGED_REPLACEMENT
            ),
            dataFields = listOf(
                DataField(
                    "owner_name",
                    R.string.full_name,
                    sectionRes = R.string.basic_data_label,
                    validation = ValidationType.ARABIC_NAME
                ),
                DataField(
                    "owner_dob",
                    R.string.birth_date,
                    type = FieldType.DATE,
                    sectionRes = R.string.basic_data_label
                ),
                DataField(
                    "owner_gov",
                    R.string.birth_governorate,
                    type = FieldType.DROPDOWN,
                    sectionRes = R.string.basic_data_label
                ),
                DataField(
                    "applicant_id",
                    R.string.national_id,
                    validation = ValidationType.NATIONAL_ID,
                    sectionRes = R.string.basic_data_label,
                    isRequired = { type ->
                        type == AppStrings.RENEWAL ||
                                type == AppStrings.LOST_REPLACEMENT ||
                                type == AppStrings.DAMAGED_REPLACEMENT
                    }
                ),
                DataField(
                    "applicant_phone",
                    R.string.phone_number,
                    type = FieldType.PHONE,
                    validation = ValidationType.PHONE,
                    sectionRes = R.string.basic_data_label
                )
            ),
            requiredFiles = listOf(
                FileRequirement("personal_photo", R.string.personal_photo_label),
                FileRequirement(
                    "birth_cert",
                    R.string.original_birth_certificate_label,
                    isRequired = { type -> type == AppStrings.ISSUANCE }
                ),
                FileRequirement(
                    "old_id",
                    R.string.old_national_id_label,
                    descriptionRes = R.string.old_id_notice,
                    minCount = 2,
                    maxCount = 2,
                    isRequired = { type ->
                        type == AppStrings.RENEWAL || type == AppStrings.DAMAGED_REPLACEMENT
                    }
                ),
                FileRequirement(
                    "police_report",
                    R.string.police_report_label,
                    descriptionRes = R.string.police_report_notice,
                    isRequired = { type -> type == AppStrings.LOST_REPLACEMENT }
                ),
            )
        ),

        ServiceTypes.MARRIAGE_CERTIFICATE to ServiceConfig(
            serviceType = ServiceTypes.MARRIAGE_CERTIFICATE,
            basePrice = 30.0,
            availableTypes = listOf(
                AppStrings.FULL_COPY,
                AppStrings.SHORT_COPY,
                AppStrings.EMBASSY_CERTIFIED,
                AppStrings.LOST_REPLACEMENT
            ),
            dataFields = listOf(
                DataField(
                    "husband_name",
                    R.string.husband_name_label,
                    sectionRes = R.string.husband_data_label,
                    validation = ValidationType.ARABIC_NAME
                ),
                DataField(
                    "husband_id",
                    R.string.husband_id_label,
                    validation = ValidationType.NATIONAL_ID,
                    sectionRes = R.string.husband_data_label
                ),
                DataField(
                    "husband_dob",
                    R.string.husband_dob_label,
                    type = FieldType.DATE,
                    sectionRes = R.string.husband_data_label
                ),
                DataField(
                    "wife_name",
                    R.string.wife_name_label,
                    sectionRes = R.string.wife_data_label,
                    validation = ValidationType.ARABIC_NAME
                ),
                DataField(
                    "wife_id",
                    R.string.wife_id_label,
                    validation = ValidationType.NATIONAL_ID,
                    sectionRes = R.string.wife_data_label
                ),
                DataField(
                    "wife_dob",
                    R.string.wife_dob_label,
                    type = FieldType.DATE,
                    sectionRes = R.string.wife_data_label
                ),
                DataField(
                    "applicant_relation",
                    R.string.relationship,
                    type = FieldType.DROPDOWN,
                    sectionRes = R.string.applicant_data,
                    relationshipType = RelationshipType.MARRIAGE
                ),
                DataField(
                    "applicant_phone",
                    R.string.phone_number,
                    type = FieldType.PHONE,
                    validation = ValidationType.PHONE,
                    sectionRes = R.string.applicant_data
                )
            ),
            requiredFiles = listOf(
                FileRequirement(
                    "husband_id_img",
                    R.string.husband_id_img_label,
                    minCount = 2,
                    maxCount = 2
                ),
                FileRequirement(
                    "wife_id_img",
                    R.string.wife_id_img_label,
                    minCount = 2,
                    maxCount = 2
                ),
                FileRequirement(
                    "marriage_contract",
                    R.string.marriage_contract_label,
                    isRequired = { type -> type != AppStrings.LOST_REPLACEMENT }
                ),
                FileRequirement(
                    "original_contract_embassy",
                    R.string.original_contract_embassy_label,
                    isRequired = { type -> type == AppStrings.EMBASSY_CERTIFIED }
                ),
                FileRequirement(
                    "police_report",
                    R.string.police_report_label,
                    descriptionRes = R.string.police_report_notice,
                    isRequired = { type -> type == AppStrings.LOST_REPLACEMENT }
                ),
            )
        ),

        ServiceTypes.DEATH_CERTIFICATE to ServiceConfig(
            serviceType = ServiceTypes.DEATH_CERTIFICATE,
            basePrice = 20.0,
            availableTypes = listOf(
                AppStrings.ISSUANCE,
                AppStrings.ADDITIONAL_COPY,
                AppStrings.LOST_REPLACEMENT
            ),
            dataFields = listOf(
                DataField(
                    "deceased_name",
                    R.string.deceased_name_label,
                    sectionRes = R.string.deceased_data_label,
                    validation = ValidationType.ARABIC_NAME
                ),
                DataField(
                    "death_date",
                    R.string.death_date_label,
                    type = FieldType.DATE,
                    sectionRes = R.string.deceased_data_label
                ),
                DataField(
                    "death_gov",
                    R.string.death_governorate_label,
                    type = FieldType.DROPDOWN,
                    sectionRes = R.string.deceased_data_label
                ),
                DataField(
                    "applicant_name",
                    R.string.applicant_name_label,
                    sectionRes = R.string.applicant_data,
                    validation = ValidationType.ARABIC_NAME
                ),
                DataField(
                    "applicant_id",
                    R.string.applicant_id_label_SR,
                    validation = ValidationType.NATIONAL_ID,
                    sectionRes = R.string.applicant_data
                ),
                DataField(
                    "applicant_relation",
                    R.string.relationship,
                    type = FieldType.DROPDOWN,
                    sectionRes = R.string.applicant_data,
                    relationshipType = RelationshipType.DEATH
                ),
                DataField(
                    "applicant_phone",
                    R.string.phone_number,
                    type = FieldType.PHONE,
                    validation = ValidationType.PHONE,
                    sectionRes = R.string.applicant_data
                )
            ),
            requiredFiles = listOf(
                FileRequirement(
                    "applicant_id_img",
                    R.string.applicant_id_img_label,
                    minCount = 2,
                    maxCount = 2
                ),
                FileRequirement(
                    "death_report",
                    R.string.death_report_label,
                    descriptionRes = R.string.death_report_notice,
                    isRequired = { type -> type == AppStrings.ISSUANCE }
                ),
                FileRequirement(
                    "original_death_cert",
                    R.string.original_death_cert_label,
                    isRequired = { type -> type == AppStrings.ADDITIONAL_COPY }
                ),
                FileRequirement(
                    "police_report",
                    R.string.police_report_label,
                    descriptionRes = R.string.police_report_notice,
                    isRequired = { type -> type == AppStrings.LOST_REPLACEMENT }
                ),
                FileRequirement(
                    "deceased_id",
                    R.string.deceased_id_optional_label,
                    isRequired = { false }
                ),
            )
        ),

        ServiceTypes.DIVORCE_CERTIFICATE to ServiceConfig(
            serviceType = ServiceTypes.DIVORCE_CERTIFICATE,
            basePrice = 30.0,
            availableTypes = listOf(
                AppStrings.JUDICIAL,
                AppStrings.AUTHORIZED_OFFICER,
                AppStrings.KHULA
            ),
            dataFields = listOf(
                DataField(
                    "husband_name",
                    R.string.husband_name_label,
                    sectionRes = R.string.husband_data_label,
                    validation = ValidationType.ARABIC_NAME
                ),
                DataField(
                    "husband_id",
                    R.string.husband_id_label,
                    validation = ValidationType.NATIONAL_ID,
                    sectionRes = R.string.husband_data_label
                ),
                DataField(
                    "husband_dob",
                    R.string.husband_dob_label,
                    type = FieldType.DATE,
                    sectionRes = R.string.husband_data_label
                ),
                DataField(
                    "wife_name",
                    R.string.wife_name_label,
                    sectionRes = R.string.wife_data_label,
                    validation = ValidationType.ARABIC_NAME
                ),
                DataField(
                    "wife_id",
                    R.string.wife_id_label,
                    validation = ValidationType.NATIONAL_ID,
                    sectionRes = R.string.wife_data_label
                ),
                DataField(
                    "wife_dob",
                    R.string.wife_dob_label,
                    type = FieldType.DATE,
                    sectionRes = R.string.wife_data_label
                ),
                DataField(
                    "applicant_relation",
                    R.string.relationship,
                    type = FieldType.DROPDOWN,
                    sectionRes = R.string.applicant_data,
                    relationshipType = RelationshipType.DIVORCE
                ),
                DataField(
                    "applicant_phone",
                    R.string.phone_number,
                    type = FieldType.PHONE,
                    validation = ValidationType.PHONE,
                    sectionRes = R.string.applicant_data
                ),
                DataField(
                    "divorce_no",
                    R.string.divorce_no_label,
                    sectionRes = R.string.divorce_data_label,
                    isRequired = { type -> type == AppStrings.JUDICIAL }
                ),
                DataField(
                    "divorce_date",
                    R.string.divorce_date_label,
                    type = FieldType.DATE,
                    sectionRes = R.string.divorce_data_label
                )
            ),
            requiredFiles = listOf(
                FileRequirement(
                    "husband_id_img",
                    R.string.husband_id_img_label,
                    minCount = 2,
                    maxCount = 2
                ),
                FileRequirement(
                    "wife_id_img",
                    R.string.wife_id_img_label,
                    minCount = 2,
                    maxCount = 2
                ),
                FileRequirement(
                    "divorce_doc",
                    R.string.divorce_doc_label
                ),
            )
        )
    )

    fun getGovernorates() = EGYPT_GOVERNORATES
}
