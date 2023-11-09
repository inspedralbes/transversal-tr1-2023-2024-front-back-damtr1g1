import pandas as pd
import matplotlib.pyplot as plt
import mysql.connector
import seaborn as sns
import schedule
import time
import subprocess
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

def obtenir_HoresComanda(cursor):
    # Consulta SQL para obtener los datos de las comandas
    consulta = """
    SELECT id, usuari, id_carret, data_comanda
    FROM Comanda
    """
    cursor.execute(consulta)
    return cursor.fetchall()

def obtener_hora_mas_dinero(cursor):
    """Obtiene la hora en la que se hace mas dinero"""
    consulta="""SELECT HOUR(data_comanda) AS hora, 
    SUM(P.preu * CP.quantitat) AS total_dinero
    FROM Comanda AS C
    INNER JOIN Carret AS CR ON C.id_carret = CR.id
    INNER JOIN Carret_Productes AS CP ON CR.id = CP.id_carret
    INNER JOIN Producte AS P ON CP.id_producte = P.id
    WHERE C.finalitzat=1
    GROUP BY HOUR(data_comanda)
    ORDER BY total_dinero DESC"""
    
    cursor.execute(consulta)
    return cursor.fetchall()

def obtenir_diners_comandes(cursor):
    """Obtiene la cantidad de dinero que hay en las comandas finalizadas y las activas"""
    consulta="""SELECT
        CASE
        WHEN C.finalitzat = 1 THEN 'Comanda finalitzada'
        ELSE 'Comanda sense finalizar'
        END AS estado_comanda,
        SUM(P.preu * CP.quantitat) AS total_dinero
        FROM Comanda AS C
        INNER JOIN Carret AS CR ON C.id_carret = CR.id
        INNER JOIN Carret_Productes AS CP ON CR.id = CP.id_carret
        INNER JOIN Producte AS P ON CP.id_producte = P.id
        GROUP BY C.finalitzat"""
    cursor.execute(consulta)
    return cursor.fetchall()

def obtenir_temps_preparacio(cursor):
    """Obtiene la media de tiempo que tarda en preparar la comanda"""

    consulta = """SELECT data_comanda,avg(temps_preparacio) FROM Comanda GROUP BY data_comanda;"""

    cursor.execute(consulta)
    return cursor.fetchall()

def graficoCantidadVendida(df, filename):
    """Crea un gráfico de barras horizontales a partir de un DataFrame."""
    plt.figure(figsize=(10, 6), facecolor='#f3f1ff')  # Establecer el color de fondo de toda la figura
    
    # Crear un gráfico de barras horizontales con puntas redondas y color personalizado
    plt.barh(df['producto'], df['cantidad_vendida'], color='#9094e9', capstyle='round')
    
    # Obtener la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    
    sns.set(style="whitegrid", rc={'axes.facecolor': '#f3f1ff'})
    
    # Utilizar la fecha actual en el título del gráfico
    plt.title(f'Unitats venudes per producte. ({fecha_actual})', fontsize=16)
   
    plt.yticks(fontsize=12)
    plt.xlabel('Quantitat', fontsize=14)
    plt.ylabel('Producte', fontsize=14)
    
    # Eliminar la cuadrícula horizontal
    plt.grid(axis='x', linestyle='--', alpha=0.7)
    
    # Guardar la figura en un archivo
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()
    
def graficoCantidad(df, filename):
    """Crea un gráfico de barras horizontales a partir de un DataFrame."""
    plt.figure(figsize=(10, 6), facecolor='#f3f1ff')  # Establecer el color de fondo de toda la figura
    
    # Crear un gráfico de barras horizontales con puntas redondas y color personalizado
    plt.barh(df['nom'], df['quantitat'], color='#9094e9', capstyle='round')
    
    # Obtener la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
   
    sns.set(style="whitegrid", rc={'axes.facecolor': '#f3f1ff'})
   
    # Utilizar la fecha actual en el título del gráfico
    plt.title(f'Unitats restants productes ({fecha_actual})', fontsize=16)
   
    plt.yticks(fontsize=12)
    plt.xlabel('Quantitat', fontsize=14)
    plt.ylabel('Productes', fontsize=14)
    
    # Eliminar la cuadrícula horizontal
    plt.grid(axis='x', linestyle='--', alpha=0.7)
    
    # Guardar la figura en un archivo
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()

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
    
def graficHoresDiners(df,filename):
    """Crea un gráfico de barras horizontales a partir de un DataFrame."""
    plt.figure(figsize=(10, 6), facecolor='#f3f1ff')
    plt.barh(df['hora'], df['total_dinero'], color='#9094e9', capstyle='round')

    # Obtener la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    # Utilizar la fecha actual en el título del gráfico
    plt.title(f'Total de diners per hora ({fecha_actual})', fontsize=16)

    plt.yticks(df['hora'], fontsize=12)  # Utiliza las horas como etiquetas en el eje y
    plt.xlabel('Total de diners', fontsize=14)
    plt.ylabel('Hora', fontsize=14)

    # Eliminar la cuadrícula horizontal
    plt.grid(axis='x', linestyle='--', alpha=0.7)

    # Guardar la figura en un archivo (reemplaza 'nombre_del_archivo' con el nombre que desees)
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()

def graficoDinersComandas(df,filename):
    """Crea un gráfico de barras horizontales a partir de un DataFrame."""
    plt.figure(figsize=(10, 6), facecolor='#f3f1ff')
    plt.barh(df['estado_comanda'], df['total_dinero'], color='#9094e9', capstyle='round')

    # Obtener la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    # Utilizar la fecha actual en el título del gráfico
    plt.title(f'Total de diners per estat de la comanda ({fecha_actual})', fontsize=16)

    plt.yticks(df['estado_comanda'], fontsize=12)  # Utiliza las horas como etiquetas en el eje y
    plt.xlabel('Total de diners', fontsize=14)
    plt.ylabel('Estat Comanda', fontsize=14)

    # Eliminar la cuadrícula horizontal
    plt.grid(axis='x', linestyle='--', alpha=0.7)

    # Guardar la figura en un archivo (reemplaza 'nombre_del_archivo' con el nombre que desees)
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()

def graficTempsPreparacio(df,filename):
    """Crea un gráfico de barras horizontales a partir de un DataFrame."""
   
    plt.figure(figsize=(10, 6), facecolor='#f3f1ff')
    
    plt.barh(df['data_comanda'], df['temps_preparacio'], color='#9094e9', capstyle='round')

    

    # Obtener la fecha actual
    fecha_actual = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    # Utilizar la fecha actual en el título del gráfico
    plt.title(f'Temps Preparacio ({fecha_actual})', fontsize=16)

    plt.yticks(df['data_comanda'], fontsize=12)  # Utiliza las horas como etiquetas en el eje y
    plt.xlabel('Total de Segons', fontsize=14)
    plt.ylabel('Estat Comanda', fontsize=14)

    # Eliminar la cuadrícula horizontal
    plt.grid(axis='x', linestyle='--', alpha=0.7)

    # Guardar la figura en un archivo (reemplaza 'nombre_del_archivo' con el nombre que desees)
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
    
def HoraDiners():
    conexion,cursor = establecer_conexion()
    resultados = obtener_hora_mas_dinero(cursor)
    print(resultados)
    conexion.close()
    
    df = pd.DataFrame(resultados, columns=['hora','total_dinero'])
    filename = './img_estadistiques/HoraMesDiners.png'
    graficHoresDiners(df,filename)

def DinersComanda():
    conexion, cursor= establecer_conexion()
    resultados = obtenir_diners_comandes(cursor)
    print(resultados)
    conexion.close()

    df = pd.DataFrame(resultados, columns=['estado_comanda','total_dinero'])
    filename = './img_estadistiques/DinersComanda.png'
    graficoDinersComandas(df,filename)

def mitjanaTempsPreparacio():
    conexion, cursor = establecer_conexion()
    resultados = obtenir_temps_preparacio(cursor)
    print(resultados)
    conexion.close()

    df = pd.DataFrame(resultados, columns=['data_comanda','temps_preparacio'])
    filename = './img_estadistiques/TempsPreparacio.png'
    graficTempsPreparacio(df,filename)

def main():
    CantidaRestante()
    CantidadVendida()
    HoraComun()
    HoraDiners()
    DinersComanda()
    mitjanaTempsPreparacio()
   
main()




