#ifndef _simulated_annealing_hpp
#define _simulated_annealing_hpp

#include <AbstractSearch.hpp>
#include <evalCC.hpp>

#include <fstream>
#include <iostream>

#include <math.h>

class SimulatedAnnealing : public AbstractSearch
{
public:
  SimulatedAnnealing(int execNumber, int evalMax, int fitnessMin) : AbstractSearch(execNumber, evalMax, fitnessMin, "sa.csv")
  {
    _temperature = 100;
    _alpha = 0.95;
    _temperatureUpdate = 15;
  }

  void run()
  {
    EvalCC eval;
    Solution s;
    s.resize(solution_size, 1);
    
    std::ofstream outputfile;
    outputfile.open(_fileName);
    outputfile << "fitness fitnessMin nbeval nbEvalMax\n";

    std::ofstream dynamique;
    dynamique.open("sa_dynamique.csv");
    dynamique << "ite fitness temperature\n";
    
    srand(time(NULL));
    
    int oldTemperature = _temperature;
    for(int i = 0; i < _execNumber; i++)
      {
	randomSolution(s);
	eval(s);
	std::cout << "starting SA" << std::endl;
	bool isOver = false;
	int bestFitness = s.fitness();
	int currentFitness;

	int evalNumber = 1;
	int iterations = 0;

	_temperature = oldTemperature;
	
	while(!isOver)
	  {
	    int randomIndex = rand() % s.size();
	    int oldNeighbour = s.neighbour(randomIndex);
	    eval(s);
	    evalNumber++;
	    currentFitness = s.fitness();
	    int delta =  currentFitness - bestFitness;

	    std::cout << " " << iterations << std::endl;
   
	    if(delta > 0)
	      {
		bestFitness = currentFitness;
	      }
	    else
	      {
		int random = rand() % 101;
		double u = random/100;
		double expo = exp((delta / (float)_temperature));
		  if(u < expo)
		    {
		      bestFitness = currentFitness;
		    }
		  else
		    {
		      s[randomIndex] = oldNeighbour;
		    }
	      }

	    if(i == 0)
	      {
		dynamique << iterations
			  << " " << bestFitness
			  << " " << _temperature <<std::endl;
	      }

	    if(iterations % _temperatureUpdate == 0) {
	      _temperature *= _alpha;
	      std::cout << "update temp to " << _temperature << std::endl;
	    }

	    if(evalNumber >= _evalMax || bestFitness <= _fitnessMin)
	      {
		_bestSolution = s;
		isOver = true;
	      }
	    
	    iterations++;
	  }

	std::cout << "-- " << i << std::endl;

	outputfile << bestFitness << " " <<
	  _fitnessMin << " " <<
	  evalNumber << " " <<
	  _evalMax << " " <<
	  std::endl;
      }
  }

private:
  float _temperature;
  float _alpha;
  int _temperatureUpdate;
};

#endif
