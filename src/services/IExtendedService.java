package services;

/**
 * Addicted interface for more complicated services that can run subservices. A
 * subservice is an implementation of {@link IService} or even of this extended
 * interface, creating trees containing subservice, extendible in a real easy
 * way and implemented with high level of independence.
 * 
 * @author Micieli
 *
 */
public interface IExtendedService extends IService {
	/**
	 * Adds subservices that the service can run, selected through his name.
	 * 
	 * @param subservices
	 *            one or more subsrvices to add
	 */
	public void addSubService(IService... subservices);

}
