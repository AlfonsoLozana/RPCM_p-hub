import math
import numpy as np

def distancia_euclidiana(x1, y1, x2, y2):
    return math.sqrt((x1 - x2)**2 + (y1 - y2)**2) / 1000



def calcular_coste_total(n, coordenadas, flujos, p, c, t, d, asignacion):
    total_cost = 0
    for i in range(1, 11):
        for j in range(1, 11):
            Hi = coordenadas[i]
            Hj = asignacion[j]
            flow = w[i][j]

            cost = (c * distance(i, Hi) + 
                    t * distance(Hi, Hj) + 
                    d * distance(Hj, j)) * flow

            total_cost += cost
        
    
            
    
    
    return coste_total

def main():
    # Datos de entrada
    n = 10
    coordinates = [
        (20355.966023, 16167.127237),
        (39988.592020, 19773.197847),
        (23572.548971, 31529.184022),
        (37304.369590, 32079.249435),
        (24512.513179, 38835.826089),
        (40867.253869, 38565.884488),
        (27520.840478, 46921.515986),
        (36067.877874, 44894.490748),
        (19345.710923, 51972.040940),
        (33635.749233, 49663.479404)
    ]
    flows = np.array([
        [75.455160, 36.992250, 54.503740, 19.269930, 20.093530, 17.650830, 56.770230, 17.080500, 18.831190, 16.385700],
        [25.793680, 38.375560, 24.997800, 26.576220, 18.289200, 23.168780, 38.161870, 20.412130, 12.482780, 16.597520],
        [66.812590, 39.315400, 51.186310, 22.408500, 24.202840, 20.514430, 70.558250, 20.242000, 22.999690, 19.424020],
        [17.329130, 33.119610, 18.761160, 24.907980, 16.361600, 22.929530, 40.161070, 23.146280, 11.420450, 18.670240],
        [17.485420, 25.137020, 21.182770, 19.093040, 23.480380, 18.306310, 73.409770, 20.087670, 23.889580, 19.434310],
        [11.607170, 18.441950, 12.168340, 16.115400, 11.117000, 16.942850, 37.166850, 21.828940, 9.556350, 18.066350],
        [82.372600, 77.535140, 90.449230, 67.791970, 95.057940, 72.635600, 312.258790, 98.826690, 110.006660, 110.368410],
        [35.143120, 41.734510, 36.299460, 48.415280, 35.711070, 57.578140, 173.373140, 89.946000, 41.226350, 91.825380],
        [12.196230, 11.899530, 15.345270, 10.239040, 18.935880, 11.158610, 67.875160, 15.064250, 23.622110, 20.372560],
        [21.717650, 24.963930, 22.806940, 27.790220, 23.083630, 32.891690, 109.098310, 50.846560, 30.247210, 63.327840]
    ])
    p = 3
    collection_cost = 3.0
    transfer_cost = 0.75
    distribution_cost = 2.0

    # Asignación de nodos a hubs
    allocation = [3, 3, 3, 3, 7, 7, 7, 7, 7, 7]

    # Calcular el coste total
    total_cost = calcular_coste_total(n, coordinates, flows, p, collection_cost, transfer_cost, distribution_cost, allocation)

    print(f'Coste total: {total_cost}')

if __name__ == "__main__":
    main()