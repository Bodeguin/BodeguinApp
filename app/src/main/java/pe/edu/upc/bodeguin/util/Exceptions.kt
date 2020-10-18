package pe.edu.upc.bodeguin.util

import java.io.IOException

class ApiException(message: String): IOException(message)

class NoInternetException(message: String): IOException(message)