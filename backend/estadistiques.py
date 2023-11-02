import pandas as pd
import matplotlib.pyplot as plt
import mysql.connector

def establecer_conexion():
    """Establece la conexión a la base de datos y devuelve el cursor."""
    conexion = mysql.connector.connect(
        host='dam.inspedralbes.cat',
        user='a22tomybanog_Projecte1',
        password='Projecte1',
        database='a22tomybanog_Projecte1'
    )
    return conexion, conexion.cursor()

def obtener_productos_ordenados_cantidad(cursor):
    """Obtiene los productos ordenados por cantidad."""
    consulta = """
    SELECT id, nom, quantitat
    FROM Producte
    ORDER BY quantitat ASC
   
    """
    cursor.execute(consulta)
    #si pongo fetchone coge solo el primero
    return cursor.fetchall()

def crear_grafico(df,filename):
    """Crea un gráfico de barras a partir de un DataFrame."""
    plt.figure(figsize=(10, 6))
    plt.bar(df['nom'], df['quantitat'])
    plt.title('Cantidad de unidades restantes por producto')
    plt.xticks(rotation=45)
    plt.xlabel('Producto')
    plt.ylabel('Cantidad')
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()

def main():
    conexion, cursor = establecer_conexion()
    resultados = obtener_productos_ordenados_cantidad(cursor)
    print(resultados)
    conexion.close()

    df = pd.DataFrame(resultados, columns=['id', 'nom', 'quantitat'])
    filename = 'backend\img_estadistiques\producteMesVenut.png'
    crear_grafico(df,filename)

main()



