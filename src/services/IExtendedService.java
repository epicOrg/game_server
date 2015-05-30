package services;

/**
 * Addicted interface for more complicated services that can run sub-services. A
 * sub-service is an implementation of {@link IService} or even of this extended
 * interface, creating trees containing sub-service, extendible in a real easy
 * way and implemented with high level of independence.
 * 
 * @author Micieli
 * @date 2015/05/24
 */

public interface IExtendedService extends IService {
	/**
	 * Adds sub-services that the service can run, selected through his name.
	 * 
	 * @param subservices
	 *            one or more sub-services to add
	 */
	public void addSubService(IService... subservices);

}
