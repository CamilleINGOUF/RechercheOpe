#ifndef _best_improvement_hpp
#define _best_improvement_hpp

#include <AbstractSearch.hpp>
#include <evalCC.hpp>

#include <fstream>
#include <iostream>

class BestImprovement : public AbstractSearch
{
public:
  BestImprovement(int execNumber, int evalMax, int fitnessMin) : AbstractSearch(execNumber, evalMax, fitnessMin, "bi.csv")
  {}

  void run()
  {
    EvalCC eval;
    Solution s;
    s.resize(solution_size, 1);
    
    std::ofstream outputfile;
    outputfile.open(_fileName);
    outputfile << "fitness fitnessMin nbeval nbEvalMax\n";

    for(int i = 0; i < _execNumber; i++)
      {
	randomSolution(s);
	eval(s);
	std::cout << "starting bestImp" << std::endl;
	bool isOver = false;
	int bestFitness = s.fitness();
	int currentFitness = bestFitness;

	int evalNumber = 1;
    
	while(!isOver)
	  {
	    int jBest = -1;
	    for(unsigned j = 0; j < s.size(); j++)
	      {
	        unsigned int oldNeighbour = s.neighbour(j);
		eval(s);
		evalNumber++;

		currentFitness = s.fitness();
		std::cout << evalNumber << " " << bestFitness << std::endl;
		if(currentFitness < bestFitness)
		  {
		    jBest = j;
		    bestFitness = currentFitness;
		  }
		else
		  {
		    s[j] = oldNeighbour;
		  }
		
		if(evalNumber >= _evalMax or bestFitness <= _fitnessMin)
		  {
		    _bestSolution = s;
		    isOver = true;
		    break;
		  }
	      }

	    if(jBest == -1)
	      isOver = true;
	  }
	
	std::cout << "-- " << i << std::endl;

	outputfile << bestFitness << " " <<
	  _fitnessMin << " " <<
	  evalNumber << " " <<
	  _evalMax << " " <<
	  std::endl;
      }
  }
};

#endif
