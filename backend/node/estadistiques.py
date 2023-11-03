import pandas as pd
import matplotlib.pyplot as plt
import mysql.connector
from datetime import datetime




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

def obtener_productos_vendidos(cursor):
    """Obtiene los productos vendidos"""
    consulta = """
    SELECT p.nom AS producto,
    SUM(cp.quantitat) AS cantidad_vendida
    FROM Producte p JOIN Carret_Productes cp
    ON p.id = cp.id_producte JOIN Comanda c
    ON cp.id_carret = c.id
    WHERE c.finalitzat = true GROUP BY p.nom;
    """
    cursor.execute(consulta)
    return cursor.fetchall()


def graficoCantidadVendida(df,filename):
    """Crea un grafico de barras a partir de un DataFrame"""
    plt.figure(figsize=(10, 6))
    plt.bar(df['producto'], df['cantidad_vendida'])
   
    # Obtiene la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
   
    # Utiliza la fecha actual en el título del gráfico
    plt.title(f'Cantidad de unidades Vendidas por producto ({fecha_actual})')
   
    plt.xticks(rotation=45)
    plt.xlabel('Producto')
    plt.ylabel('Cantidad')
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()
    
def graficoCantidad(df, filename):
    """Crea un gráfico de barras a partir de un DataFrame."""
    plt.figure(figsize=(10, 6))
    plt.bar(df['nom'], df['quantitat'])
   
    # Obtiene la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
   
    # Utiliza la fecha actual en el título del gráfico
    plt.title(f'Cantidad de unidades restantes por producto ({fecha_actual})')
   
    plt.xticks(rotation=45)
    plt.xlabel('Producto')
    plt.ylabel('Cantidad')
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()

def obtenir_HoresComanda(cursor):
    # Consulta SQL para obtener los datos de las comandas
    consulta = """
    SELECT id, usuari, id_carret, data_comanda
    FROM Comanda
    """
    cursor.execute(consulta)
    return cursor.fetchall()

def graficHoresComanda(df,filename,hora_mas_comun):
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    hora_counts = df['hora_creacion'].value_counts().sort_index()
    plt.bar(hora_counts.index, hora_counts.values)
    plt.title(f'Comandas por hora de creación ({fecha_actual}) ')
    plt.xlabel(f'Hora del día mas comun ({hora_mas_comun})')
    plt.ylabel('Número de comandas')
    plt.xticks(range(24))  # Etiquetas para las 24 horas
    plt.savefig(filename)
    plt.close()
    

    

    
def CantidaRestante():
    conexion, cursor = establecer_conexion()
    resultados = obtener_productos_ordenados_cantidad(cursor)
    print(resultados)
    conexion.close()


    df = pd.DataFrame(resultados, columns=['id', 'nom', 'quantitat'])
    filename = './img_estadistiques/producteCantidad.png'
    graficoCantidad(df,filename)
   
def CantidadVendida():
    conexion, cursor = establecer_conexion()
    resultados = obtener_productos_vendidos(cursor)
    print(resultados)
    conexion.close()
   
    df = pd.DataFrame(resultados, columns=['producto', 'cantidad_vendida'])
    filename = './img_estadistiques/producteMesVenut.png'
    graficoCantidadVendida(df,filename)
    
def HoraComun():
    conexion, cursor = establecer_conexion()
    resultados = obtenir_HoresComanda(cursor)
    print(resultados)
    conexion.close()
    
    df = pd.DataFrame(resultados, columns=['id', 'usuari', 'id_carret', 'data_comanda'])
    df['data_comanda'] = pd.to_datetime(df['data_comanda'])

    # Extraer la hora de 'data_comanda'
    df['hora_creacion'] = df['data_comanda'].dt.hour

    # Calcular la hora más común
    hora_mas_comun = df['hora_creacion'].mode().values[0]
    filename = './img_estadistiques/HoraMesComu.png'
    graficHoresComanda(df,filename,hora_mas_comun)
    
def main():
    CantidaRestante()
    CantidadVendida()
    HoraComun()
   
main()




