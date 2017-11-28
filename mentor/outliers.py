#!/usr/bin/python3
# -*- coding: utf8 -*-

from math import sqrt

# Computes the euclidean distance of vectors u and v 
def euclidean_distance(u, v): return sqrt(sum([(au-av)**2 for au,av in zip(u, v)])) 

def k_nearest_neighbors(k, p, data, dist=euclidean_distance):
    data1 = data.copy()
    data1.remove(p)
    distances = {}
    for obj in data1:
        distances[obj] = dist(p, obj)
    distances = sorted(distances.values())
    neighbors = [distances.keys()][:k]
    k_dist = distances[neighbors[-1]]
    return k_dist, neighbors

def reachability_distance(k, p, o, data, dist=euclidean_distance):
    k_dist, neighbors = k_nearest_neighbors(k, o, data, dist=dist)
    return max(k_dist, dist(p, o))
