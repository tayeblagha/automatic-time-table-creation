package com.alialperen.timeTableGenerator.geneticAlgorithm;
//
//import com.alialperen.timeTableGenerator.entity.*;
//import com.alialperen.timeTableGenerator.entity.Module;
//import com.alialperen.timeTableGenerator.entity.enums.Period;
//
//import java.time.DayOfWeek;
//import java.util.*;
//
//import com.alialperen.timeTableGenerator.config.DataFromDb;
//
public class GeneticAlgorithm {
//
//	private final int POPULATION_SIZE = 10;
//	private final double MUTATION_RATE=0.2;
//	private final double CROSSOVER_RATE = 0.1;
//	private final int MAX_GENERATIONS = 50;
//
//	Period[] timeSlots;
//	List<DayOfWeek> days;
//
//	private final int targetFitness = 1;
//	Random random = new Random();
//	private List<TimeTable> population;
//	private boolean isTerminated;
//
//
//	public GeneticAlgorithm() {
//		this.population = new ArrayList<>(POPULATION_SIZE);
//		this.isTerminated = false;
//
//		timeSlots = Period.values();
//
//		days = new ArrayList<>();
//        days.add(DayOfWeek.MONDAY);
//        days.add(DayOfWeek.TUESDAY);
//        days.add(DayOfWeek.WEDNESDAY);
//        days.add(DayOfWeek.THURSDAY);
//        days.add(DayOfWeek.FRIDAY);
//
//	}
//
//
//
//
//
//	public boolean hasConflict(TimeTable timetable, int classIndex, DayOfWeek day, Period period) {
//	    List<ModuleElement> classSchedule = timetable.getTimeTable(classIndex);
//
//	    for (ModuleElement element : classSchedule) {
//	        if (element.getDay().equals(day) && element.getPeriod().equals(period)) {
//	            return true;
//	        }
//	    }
//
//	    // Aynı sınıfta birden fazla ders olup olmadığını kontrol et
//	    Set<String> seenModules = new HashSet<>();
//	    for (ModuleElement element : classSchedule) {
//	        if (element.getDay().equals(day) && element.getPeriod().equals(period)) {
//	            String moduleLabel = element.getModule().getLabel();
//	            if (seenModules.contains(moduleLabel)) {
//	                return true;
//	            }
//	            seenModules.add(moduleLabel);
//	        }
//	    }
//
//	    return false;
//	}
//
//	private boolean hasConflictInClass(List<ModuleElement> classSchedule, DayOfWeek day, Period period) {
//	    Set<String> seenModules = new HashSet<>();
//	    for (ModuleElement element : classSchedule) {
//	        if (element.getDay().equals(day) && element.getPeriod().equals(period)) {
//	            String moduleLabel = element.getModule().getLabel();
//	            if (seenModules.contains(moduleLabel)) {
//	                return true;
//	            }
//	            seenModules.add(moduleLabel);
//	        }
//	    }
//	    return false;
//	}
//
//
//
//
//
//	private DayOfWeek getRandomDay() {
//        int index = random.nextInt(days.size());
//        return days.get(index);
//    }
//
//	private Period getRandomPeriod() {
//		int index = random.nextInt(timeSlots.length);
//		return timeSlots[index];
//	}
//
//    private List<ModuleElement> getElementsForClasse(Classes classes) {
//        List<ModuleElement> moduleElements = new ArrayList<>();
//        for (Module module : classes.getModules()) {
//        	moduleElements.addAll(module.getModuleElements());
//        }
//        return moduleElements;
//    }
//
//
//
//    private int getRandomClassroom(ModuleElement moduleElement) {
//        int sumNbr = moduleElement.getPeopleTakingCourse();
//        while (true) {
//            int index = random.nextInt(DataFromDb.classrooms.size());
//
//
//            if (DataFromDb.classrooms.get(index).getCapacity() >= sumNbr) {
//                return index;
//            }
//        }
//    }
//
//
//
//
//	public void initializePopulation() {
//	    for (int i = 0; i < POPULATION_SIZE; i++) {
//	    	TimeTable schoolTimetable = new TimeTable(DataFromDb.lessons.size());
//	        for (int classIndex = 0; classIndex < DataFromDb.lessons.size(); classIndex++) {
//	            Classes classes = DataFromDb.lessons.get(classIndex);
//	            List<ModuleElement> elements = getElementsForClasse(classes);
//
//	            Collections.shuffle(elements);
//
//	            for (ModuleElement element : elements) {
//	                DayOfWeek day;
//	                Period period;
//	                int roomIndex;
//	                boolean isConflicting;
//	                do {
//	                    day = getRandomDay();
//	                    period = getRandomPeriod();
//	                    roomIndex = getRandomClassroom(element);
//	                    isConflicting = hasConflict(schoolTimetable, classIndex, day, period);
//
//	                } while (isConflicting);
//
//	                element.setDay(day);
//	                element.setPeriod(period);
//	                element.setClassroom(DataFromDb.classrooms.get(roomIndex));
//	                schoolTimetable.addModuleElement(classIndex, element);
//	            }
//	        }
//	        population.add(schoolTimetable);
//	    }
//	}
//
//
//
//
//
//    public void printTimetable(TimeTable schoolTimetable) {
//        System.out.println("SchoolTimetable with fitness : " + schoolTimetable.getFitness() + " %");
//        for (int classIndex = 0; classIndex < schoolTimetable.getNumberOfLessons(); classIndex++) {
//            System.out.println("Class " + schoolTimetable.getTimeTable(classIndex).get(0).getModule().getLesson().getLabel() + ":");
//            List<ModuleElement> classTimetable = schoolTimetable.getTimeTable(classIndex);
//            System.out.println("classTimetable size " + classTimetable.size());
//            for (ModuleElement element : classTimetable) {
//				String day = element.getDay().toString();
//				String period = element.getPeriod().toString();
//				String classroom = element.getClassroom().getTypeRoom()+ " " + element.getClassroom().getNumRoom();
//				String module = element.getModule().getLabel();
//
//				 System.out.println("Day: " + day + ", Period: " + period + ", Room: " + classroom + ", Module: " + module + ", Teacher: " + element.getTeacher().getFirstName() + " Element: " + element.getLabel());
//            }
//            System.out.println();
//        }
//    }
//
//    public void evolve() {
//        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
//            List<TimeTable> newPopulation = new ArrayList<>(POPULATION_SIZE);
//
//            for (int j = 0; j < POPULATION_SIZE / 2; j++) {
//            	TimeTable parent1 = selectParent();
//            	TimeTable parent2 = selectParent();
//
//                if (random.nextDouble() <= CROSSOVER_RATE) {
//                    List<TimeTable> children = crossover(parent1, parent2);
//
//                    if (random.nextDouble() <= MUTATION_RATE) {
//                        mutate(children.get(0));
//                    }
//                    if (random.nextDouble() <= MUTATION_RATE) {
//                        mutate(children.get(1));
//                    }
//
//                    newPopulation.add(children.get(0));
//                    newPopulation.add(children.get(1));
//                } else {
//                    newPopulation.add(parent1);
//                    newPopulation.add(parent2);
//                }
//            }
//
//
//            for (TimeTable schoolTimetable : newPopulation) {
//                schoolTimetable.calculateFitness();
//            }
//
//            population = newPopulation;
//
//            System.out.println("Generation " + generation + " with population size: " + population.size());
//            for (TimeTable schoolTimetable : population) {
//                System.out.println(schoolTimetable.getFitness());
//            }
//
//
//            if ( getBestTimetable().getFitness() <= targetFitness) {
//                isTerminated = true;
//                break;
//            }
//        }
//    }
//
//    public TimeTable getBestTimetable() {
//        // if (isTerminated) {
//    	TimeTable bestSchoolTimetable = population.get(0);
//        double bestFitness = bestSchoolTimetable.calculateFitness();
//
//        for (int i = 1; i < POPULATION_SIZE; i++) {
//        	TimeTable currentSchoolTimetable = population.get(i);
//            double currentFitness = currentSchoolTimetable.calculateFitness();
//
//            if (currentFitness < bestFitness) {
//                bestSchoolTimetable = currentSchoolTimetable;
//                bestFitness = currentFitness;
//            }
//        }
//
//        return bestSchoolTimetable;
//    }
//
//    private TimeTable selectParent() {
//        int totalFitness = 0;
//        for (TimeTable schoolTimetable : population) {
//            totalFitness += schoolTimetable.calculateFitness();
//        }
//
//        int randomFitness;
//        if (totalFitness > 0) {
//            randomFitness = random.nextInt(totalFitness);
//        } else {
//            return population.get(0);
//        }
//
//        int cumulativeFitness = 0;
//        for (TimeTable schoolTimetable : population) {
//            cumulativeFitness += (totalFitness - schoolTimetable.calculateFitness());
//            if (cumulativeFitness > randomFitness) {
//                return schoolTimetable;
//            }
//        }
//        return population.get(random.nextInt(population.size()));
//    }
//
//
//    public List<TimeTable> crossover(TimeTable parent1, TimeTable parent2) {
//        Random random = new Random();
//        int numberOfClasses = parent1.getNumberOfLessons();
//
//        TimeTable offspring1 = new TimeTable(numberOfClasses);
//        TimeTable offspring2 = new TimeTable(numberOfClasses);
//
//        for (int classIndex = 0; classIndex < numberOfClasses; classIndex++) {
//            List<ModuleElement> parent1Timetable = parent1.getTimeTable(classIndex);
//            List<ModuleElement> parent2Timetable = parent2.getTimeTable(classIndex);
//
//            int timetableSize = parent1Timetable.size();
//
//            List<ModuleElement> child1Timetable = new ArrayList<>();
//            List<ModuleElement> child2Timetable = new ArrayList<>();
//
//            for (int i = 0; i < timetableSize; i++) {
//                ModuleElement element1 = parent1Timetable.get(i);
//                ModuleElement element2 = parent2Timetable.get(i);
//
//                // Çakışma kontrolü ve çocuk bireylerin oluşturulması
//                if (!hasConflictInClass(child1Timetable, element1.getDay(), element1.getPeriod())) {
//                    child1Timetable.add(element1);
//                }
//                if (!hasConflictInClass(child2Timetable, element2.getDay(), element2.getPeriod())) {
//                    child2Timetable.add(element2);
//                }
//            }
//
//            offspring1.getTimeTable(classIndex).addAll(child1Timetable);
//            offspring2.getTimeTable(classIndex).addAll(child2Timetable);
//        }
//
//        List<TimeTable> offspring = new ArrayList<>();
//        offspring.add(offspring1);
//        offspring.add(offspring2);
//
//        return offspring;
//    }
//
//    private void mutate(TimeTable schoolTimetable) {
//        Random random = new Random();
//
//        int classIndex = random.nextInt(schoolTimetable.getNumberOfLessons());
//        List<ModuleElement> classTimetable = schoolTimetable.getTimeTable(classIndex);
//
//        int position = random.nextInt(classTimetable.size());
//
//        // Rastgele seçilen bir modül elemanının, rastgele seçilen bir gün ve periyotta atanması
//        DayOfWeek randomDay = getRandomDay();
//        Period randomPeriod = getRandomPeriod();
//
//        ModuleElement elementToMutate = classTimetable.get(position);
//        if (!hasConflictInClass(classTimetable, randomDay, randomPeriod)) {
//            elementToMutate.setDay(randomDay);
//            elementToMutate.setPeriod(randomPeriod);
//        }
//    }
//
//
//
//    public TimeTable generateTimetable() {
//        initializePopulation();
//        evolve();
//
//        return getBestTimetable();
//    }

}

