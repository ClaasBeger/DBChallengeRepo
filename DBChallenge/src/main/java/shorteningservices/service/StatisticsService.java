package shorteningservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shorteningservices.entity.CallStatistics;
import shorteningservices.entity.URL;
import shorteningservices.exception.StatisticsNotFoundException;
import shorteningservices.exception.URLNotFoundException;
import shorteningservices.repository.StatisticsRepository;
import shorteningservices.repository.URLRepository;

@Service
public class StatisticsService {

	@Autowired
	private StatisticsRepository statsRepo;

	public CallStatistics saveStats(CallStatistics newStats) {
		return statsRepo.save(newStats);
	}

	public Iterable<CallStatistics> findAll() {
		return statsRepo.findAll();
	}

	public CallStatistics findByID(int id) {
		return statsRepo.findById(id).orElseThrow(() -> new StatisticsNotFoundException(id));
	}

	public void deleteById(int id) {

		try {
			statsRepo.deleteById(id);
		} catch (Exception e) {
			System.err.println("Unable to delete Statistics with ID: " + id);
		}

	}
}
