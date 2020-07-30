package training.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import training.model.PackageElement;
import training.service.PackageElementService;

@Component
public class CheckIsTrainingUtil {
	
	@Autowired
	PackageElementService packageElementService;
	
	@PostConstruct
	public void checkIsTraining() throws Exception {
		List<PackageElement> packageElements = packageElementService.findAll();
		System.out.println("Check is training!");
		boolean isTrainingElementPresent = false;
		for(PackageElement packageElement : packageElements) {
			if(packageElement != null) {
				if(packageElement.isIsProtected()){
					isTrainingElementPresent = true;
				}
			}
		}
		if(!isTrainingElementPresent) {
			PackageElement packageElement = new PackageElement();
			packageElement.setDescription("Trening element paketa.");
			packageElement.setPackageElementName("Trening");
			packageElement.setIsProtected(true);
			packageElement.setPackageElementID(null);
			packageElementService.save(packageElement);
		}
	}
}
