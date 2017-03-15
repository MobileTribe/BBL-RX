//
//  AutoCompleteViewController.swift
//  RxDemo
//
//  Created by Aurélien DELRUE on 22/12/2016.
//  Copyright © 2016 Aurélien DELRUE. All rights reserved.
//

import UIKit
import GooglePlaces
import RxCocoa
import RxSwift

class AutoCompleteViewController: UIViewController {
    
    /// UI
    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var tableView: UITableView!
    
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        setupRx()
        searchBar.becomeFirstResponder()
    }
    
    
    
    // MARK: Rx
    
    // Setup observers
    func setupRx() {
        
        // Observable on search bar input
        _ = self.searchBar
            .rx.text.orEmpty
            .debounce(0.5, scheduler: MainScheduler.instance)
            .filter { $0.characters.count > 2 }
            
            // Search suggestion for latest term
            .flatMap { term -> Observable<[AnyObject]> in
                
                if term.isEmpty {
                    return Observable.empty()
                }
                
                return self.placeAutocompleteWithTerm(term: term)
            }
            .subscribeOn(ConcurrentDispatchQueueScheduler(qos: .background))
            .observeOn(MainScheduler.instance)
            
            // Bind to tableview
            .bindTo(tableView.rx.items(cellIdentifier: "Cell")) {
                (index, result: AnyObject, cell) in
                
                if let prediction = result as? GMSAutocompletePrediction {
                    cell.textLabel?.attributedText = prediction.attributedFullText
                }
        }
    }
}

// Network
extension AutoCompleteViewController {
    
    func placeAutocompleteWithTerm(term: String) -> Observable<[AnyObject]> {
        
        return Observable<[AnyObject]>.create { (observer) -> Disposable in
            
            let filter = GMSAutocompleteFilter()
            filter.type = GMSPlacesAutocompleteTypeFilter.noFilter
            
            // Search places
            print("Send request with '\(term)'")
            GMSPlacesClient.shared().autocompleteQuery(term, bounds: nil, filter: filter, callback: { (results, error) in
                
                if results == nil {
                    observer.on(Event.error(error!))
                } else {
                    observer.on(Event.next(results!))
                    observer.onCompleted()
                }
            })
            
            return Disposables.create()
        }
    }
}
