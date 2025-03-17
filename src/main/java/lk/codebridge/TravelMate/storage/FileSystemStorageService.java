package lk.codebridge.TravelMate.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	public FileSystemStorageService(StorageProperties properties) {

		if (properties.getLocation().trim().length() == 0) {
			throw new StorageException("File upload location can not be Empty.");
		}

		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file, String customFileName) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + customFileName);
			}

			// Ensure custom file name is unique if required, or just use the provided
			// custom name
			String extension = getFileExtension(file.getOriginalFilename()); // Function to get file extension
			String newFileName = customFileName + ".png";

			// Copy the file using the custom file name
			Files.copy(file.getInputStream(), this.rootLocation.resolve(newFileName));
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + customFileName, e);
		}
	}

	// Utility method to extract the file extension from the original filename
	private String getFileExtension(String filename) {
		int index = filename.lastIndexOf('.');
		return index > 0 ? filename.substring(index) : "";
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
