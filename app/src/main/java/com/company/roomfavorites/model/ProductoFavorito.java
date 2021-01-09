package com.company.roomfavorites.model;


/* Esta clase es para almacenar el resultado de la consulta
        "SELECT Producto.id, Producto.nombre, CASE WHEN userId IS NOT NULL THEN 1 ELSE 0 END as esFavorito FROM Producto LEFT JOIN (SELECT * FROM Favorito WHERE userId = :userId) AS Fav ON Producto.id = Fav.productoId"

    Es decir, toda la lista de productos con un campo extra "esFavorito", que indica para cada Producto
    si es favorito para un usuario determinado (:userId)
*/

public class ProductoFavorito {
    public int id;
    public String nombre;
    public boolean esFavorito;
}
