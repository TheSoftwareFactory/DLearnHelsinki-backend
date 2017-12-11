#!/usr/bin/python3
# -*- coding: utf8 -*-


"""

Distance metrics and related functions

"""


# Computes the City Block (Manhanttan) distance of vectors u and v
def cityblock(u, v): return minkowski(u, v, 1)


# Computes the euclidean distance of vectors u and v 
def euclidean(u, v): return minkowski(u, v, 2)


# Computes the square of euclidean distance of vectors u and v
def sqeuclidean(u, v): return euclidean(u, v)**2


# Computes the p:th Minkoski distance of vectors u and v
def minkowski(u, v, p=2): return sum([abs(au-av)**p for au,av in zip(u, v)])**(1/p)


"""

Local outlier factor related function
www.dbs.ifi.lmu.de/Publikationen/Papers/LOF.pdf

"""


# KNN algorithm aggording to \cite. Returns also the k-distance of p
def k_nearest_neighbors(k, p, data, dist=euclidean):
    data1 = data.copy()
    data1.remove(p)
    distances = {}
    for obj in data1:
        distances[obj] = dist(p, obj)
    distances = sorted(distances.values())
    neighbors = [distances.keys()][:k]
    k_dist = distances[neighbors[-1]]
    return k_dist, neighbors


# Reachability distance of a vector p with respect to vector o
def reachability_distance(k, p, o, data, dist=euclidean):
    k_dist, neighbors = k_nearest_neighbors(k, o, data, dist=dist)
    return max(k_dist, dist(p, o))


# Local reachability denśity of a vector p
def local_reachability_density(min_pts, p, data, dist=euclidean):
    k_dist, neighbors = k_nearest_neighbors(min_pts, p, data, dist=dist)
    reach_distances = [reach_distance(p, o) for o in neighbors]
    lrd = len(neighbors) / sum(reach_distances)
    return lrd


# Local outlier factor of a vector p
def local_outlier_factor(min_pts, p, data, dist=euclidean):
    k_dist, neighbors = k_nearest_neighbors(min_pts, p, data, dist=dist)
    lrd_p = local_reachability_density(min_pts, p, data, dist=dist)
    lrd_ratios = [local_reachability_density(o) / lrd_p for o in neighbors]
    lof = sum(lrd_ratios) / len(neighbors)
    return lof


# According to the paper anything under lof-score of 1.0 is not a local oulier
def outliers(min_pts, data, dist=euclidean):
    outliers = []
    for p in data:
        lof = local_outlier_factor(min_pts, p, data, dist=euclidean)
        if lof > 1.0:
            outliers.append({'score': lof, 'object': p})
        outliers.sort(key=lambda p: p['score'], reverse=True)
    return outliers
