package com.alialperen.timeTableGenerator.service;
import java.util.*;

import com.alialperen.timeTableGenerator.entity.*;
public interface ModuleElementService {

	
    List<ModuleElement> getModuleElement();

    ModuleElement addModuleElement(ModuleElement moduleElement);

    String deleteModuleElement(Long id);

    ModuleElement getModuleElementById(Long id);

    ModuleElement updateModuleElement(Long id, ModuleElement moduleElement);

    List<ModuleElement> getSchedulesByClass(Long classId);
}
