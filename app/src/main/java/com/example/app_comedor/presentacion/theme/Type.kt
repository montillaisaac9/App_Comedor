import com.example.app_comedor.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val PoppinsFont = FontFamily(
    Font(R.font.poppins_regular,FontWeight.Normal),
    Font(R.font.poppins_bold,FontWeight.Bold),
    Font(R.font.poppins_semi_bold,FontWeight.SemiBold),
    Font(R.font.poppins_thin,FontWeight.Thin),
    Font(R.font.poppins_black,FontWeight.Black),
    Font(R.font.poppins_extra_bold,FontWeight.ExtraBold),
    Font(R.font.poppins_extra_light,FontWeight.ExtraLight),
    Font(R.font.poppins_light,FontWeight.Light),
    Font(R.font.poppins_medium,FontWeight.Medium),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = PoppinsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),
    bodySmall = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    displayLarge = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 57.sp,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 49.sp,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 41.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineLarge = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = PoppinsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleSmall = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),
    labelLarge = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    labelMedium = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    ),
    labelSmall = TextStyle(
        fontFamily = PoppinsFont,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )

    /* Other default text styles to override
titleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,0
    lineHeight = 28.sp,
    letterSpacing = 0.sp
),
labelSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)
*/
)