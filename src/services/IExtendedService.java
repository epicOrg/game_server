package services;


public interface IExtendedService extends IService{

	void addSubService(IService ... subservices); 

}
