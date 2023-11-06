import pandas as pd
import matplotlib.pyplot as plt
import mysql.connector
import seaborn as sns

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


def graficoCantidadVendida(df, filename):
    """Crea un gráfico de barras a partir de un DataFrame."""
    plt.figure(figsize=(10, 6), facecolor='#f3f1ff')  # Establecer el color de fondo de toda la figura
    
    # Crear un gráfico de barras con puntas redondas y color personalizado
    plt.bar(df['cantidad_vendida'], df['producto'], color='#9094e9', capstyle='round')
    
    # Obtener la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
   
    # Utilizar la fecha actual en el título del gráfico
    plt.title(f'Unitats venudes per producte. ({fecha_actual})', fontsize=16)
   
    plt.xticks(fontsize=12)
    plt.ylabel('Producte', fontsize=14)
    plt.xlabel('Quantitat', fontsize=14)
    
    # Eliminar la cuadrícula horizontal
    plt.grid(axis='y', linestyle='--', alpha=0.7)
    
    # Guardar la figura en un archivo
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()
    
def graficoCantidad(df, filename):
    """Crea un gráfico de barras a partir de un DataFrame."""
    plt.figure(figsize=(10, 6), facecolor='#f3f1ff')  # Establecer el color de fondo de toda la figura
    
    # Crear un gráfico de barras con puntas redondas y color personalizado
    plt.bar(df['quantitat'], df['nom'], color='#9094e9', capstyle='round')
    
    # Obtener la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
   
    # Utilizar la fecha actual en el título del gráfico
    plt.title(f'Unitats restants productes({fecha_actual})', fontsize=16)
   
    plt.xticks(fontsize=12)
    plt.ylabel('Productes', fontsize=14)
    plt.xlabel('Quantitat', fontsize=14)
    
    # Eliminar la cuadrícula horizontal
    plt.grid(axis='y', linestyle='--', alpha=0.7)
    
    # Guardar la figura en un archivo
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

def graficHoresComanda(df, filename, hora_mas_comun):
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    hora_counts = df['hora_creacion'].value_counts().sort_index()
    
    # Crear un gráfico de barras con puntas redondas y color personalizado
    plt.figure(figsize=(10, 6), facecolor='#f3f1ff')  # Establecer el color de fondo de toda la figura
    
    # Configuración de Seaborn para el color de las barras y otras características
    sns.set(style="whitegrid", rc={'axes.facecolor': '#f3f1ff'})
    
    ax = sns.barplot(x=hora_counts.index, y=hora_counts.values, color='#9094e9', capstyle='round')
    
    plt.title(f'Comandes per hora de creació({fecha_actual})', fontsize=16)
    plt.xlabel(f'Hora del dia més comuna.({hora_mas_comun})', fontsize=14)
    plt.ylabel('Número de comandes', fontsize=14)
    plt.xticks(range(24), fontsize=12)  # Etiquetas para las 24 horas
    
    # Rotar las etiquetas del eje x para mayor legibilidad
    ax.set_xticklabels(ax.get_xticklabels(), horizontalalignment='right')
    
    # Guardar la figura en un archivo
    plt.tight_layout()
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




