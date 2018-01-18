/**
 * @file main.cpp
 * See the AUTHORS or Authors.txt file
 */

/*
 * Copyright (C) 2017-2018 ULCO http://www.univ-litoral.fr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#include <graph_manager.hpp>
#include <models.hpp>

#include <artis-star/common/RootCoordinator.hpp>

#include <solution.hpp>
#include <evalCC.hpp>

#include <chrono>
#include <fstream>
#include <iostream>

#include <time.h>

#include <AbstractSearch.hpp>
#include <RandomWalk.hpp>
#include <RandomSearch.hpp>
#include <FirstImprovement.hpp>
#include <BestImprovement.hpp>

//using namespace cc;
//using namespace artis::common;

/*
class View : public cc::View
{
public:
    View()
    {
        selector("Stack:height", { RootGraphManager::CC,
                    SubGraphManager::STACK, cc::View::ALL, Stack::HEIGHT });
        selector("Crane:moveNumber", { RootGraphManager::CC,
                    SubGraphManager::CRANE, Crane::MOVE_NUMBER });
        selector("Crane:slabNumber", { RootGraphManager::CC,
                    SubGraphManager::CRANE, Crane::SLAB_NUMBER });
    }
};

void stat(const artis::common::RootCoordinator < DoubleTime,
          artis::pdevs::Coordinator < DoubleTime, RootGraphManager,
          GlobalParameters > >& rc)
{
    unsigned int sum = 0;
    unsigned int sum_N = 0;

    for (unsigned int i = 1; i <= 5; ++i) {
        const ::View::Values& values = rc.observer().view("CC").get(
            "Stack:height", (boost::format(":root:CC:stack_%1%:height") %
                             i).str());

        for (::View::Values::const_iterator it = values.begin();
             it != values.end(); ++it) {
            int height;

            it->second(height);
            sum += height;
        }
        sum_N += values.size();
    }
    std::cout << "Average of stack height = " << ((double)sum / sum_N)
              << std::endl;

    {
        const ::View::Values& values = rc.observer().view("CC").get(
            "Crane:moveNumber");
        int move_number;

        values.back().second(move_number);
        std::cout << "Crane move number: " << move_number << std::endl;
    }
    {
        const ::View::Values& values = rc.observer().view("CC").get(
            "Crane:slabNumber");
        int slab_number;

        values.back().second(slab_number);
        std::cout << "Total slab number: " << slab_number << std::endl;
    }
}
*/
void random_solution(Solution & solution) {
  srand(time(NULL));
  for(unsigned i = 0; i < solution.size(); i++) {
    int r = rand() % 101;
    solution[i] = r;
  }
}

int random_search(Solution & solution, int nb_eval_max) {
  EvalCC eval;
  
  Solution buffer = solution;
  int best_fitness = solution.fitness();
  for(signed i = 0; i < nb_eval_max; i++) {
    
    random_solution(buffer);
    eval(buffer);
    int fitness = buffer.fitness();
    
    if(fitness < best_fitness) {
      solution = buffer;
      best_fitness = fitness;
    }
  }

  eval(solution);
  return best_fitness;
}

int main()
{
  // Exec Number MaxEval MinFitness
  AbstractSearch * search = new BestImprovement(10,30,100);
  search->run();
  
  return 0;
}
