# DS-Assignment

Notes:
- All simulations should have a 2D ArrrayList that stores the path of all vehicles.

Overall Updates:
- Removed the N type parameter for all classes because it is unused.

Some explanations for the graph class: 
1. calculateDistance(T src, T dest) method is used to calculate distance between 2 points/vertices - hence, it is the **weight of the edge** between 2 points. 
- note: i changed up the name of this method from previous 
2. calculateCost(ArrayList) - is used to calculate and print the tour cost, and capacity of one vehicle
3. calculateTour(2D ArrayList) - calculates and prints total tour cost including everything.
