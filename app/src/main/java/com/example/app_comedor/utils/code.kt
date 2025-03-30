package com.example.app_comedor.utils

import android.content.Context
import com.example.app_comedor.R

fun code(code: Int?, context: Context): String{
    return when(code){
        /** Respuestas informativas */
        100 -> context.getString(R.string.CODE100)
        101 -> context.getString(R.string.CODE101)
        102 -> context.getString(R.string.CODE102)
        103 -> context.getString(R.string.CODE103)

        /** Respuestas satisfactorias */
        200 -> context.getString(R.string.CODE200)
        201 -> context.getString(R.string.CODE201)
        202 -> context.getString(R.string.CODE202)
        203 -> context.getString(R.string.CODE203)
        204 -> context.getString(R.string.CODE204)
        205 -> context.getString(R.string.CODE205)
        206 -> context.getString(R.string.CODE206)
        207 -> context.getString(R.string.CODE207)
        208 -> context.getString(R.string.CODE208)
        226 -> context.getString(R.string.CODE226)

        /** Redirecciones */
        300 -> context.getString(R.string.CODE300)
        301 -> context.getString(R.string.CODE301)
        302 -> context.getString(R.string.CODE302)
        303 -> context.getString(R.string.CODE303)
        304 -> context.getString(R.string.CODE304)
        305 -> context.getString(R.string.CODE305)
        306 -> context.getString(R.string.CODE306)
        307 -> context.getString(R.string.CODE307)
        308 -> context.getString(R.string.CODE308)

        /** Errores de cliente */
        400 -> context.getString(R.string.CODE400)
        401 -> context.getString(R.string.CODE401)
        402 -> context.getString(R.string.CODE402)
        403 -> context.getString(R.string.CODE403)
        404 -> context.getString(R.string.CODE404)
        405 -> context.getString(R.string.CODE405)
        406 -> context.getString(R.string.CODE406)
        407 -> context.getString(R.string.CODE407)
        408 -> context.getString(R.string.CODE408)
        409 -> context.getString(R.string.CODE409)
        410 -> context.getString(R.string.CODE410)
        411 -> context.getString(R.string.CODE411)
        412 -> context.getString(R.string.CODE412)
        413 -> context.getString(R.string.CODE413)
        414 -> context.getString(R.string.CODE414)
        415 -> context.getString(R.string.CODE415)
        416 -> context.getString(R.string.CODE416)
        417 -> context.getString(R.string.CODE417)
        418 -> context.getString(R.string.CODE418)
        421 -> context.getString(R.string.CODE421)
        422 -> context.getString(R.string.CODE422)
        423 -> context.getString(R.string.CODE423)
        424 -> context.getString(R.string.CODE424)
        426 -> context.getString(R.string.CODE426)
        428 -> context.getString(R.string.CODE428)
        429 -> context.getString(R.string.CODE429)
        431 -> context.getString(R.string.CODE431)
        451 -> context.getString(R.string.CODE451)
        499 -> context.getString(R.string.CODE499)

        /** Errores de servidor */
        500 -> context.getString(R.string.CODE500)
        501 -> context.getString(R.string.CODE501)
        502 -> context.getString(R.string.CODE502)
        503 -> context.getString(R.string.CODE503)
        504 -> context.getString(R.string.CODE504)
        505 -> context.getString(R.string.CODE505)
        506 -> context.getString(R.string.CODE506)
        507 -> context.getString(R.string.CODE507)
        508 -> context.getString(R.string.CODE508)
        510 -> context.getString(R.string.CODE510)
        511 -> context.getString(R.string.CODE511)
        521 -> context.getString(R.string.CODE521)
        525 -> context.getString(R.string.CODE525)
        else -> context.getString(R.string.Failed)
    }
}