//
//  DemoViewController.swift
//  RxDemo
//
//  Created by Aurélien DELRUE on 22/12/2016.
//  Copyright © 2016 Aurélien DELRUE. All rights reserved.
//

import UIKit
import RxSwift


class DemoViewController: UIViewController {
    
    /// UI
    @IBOutlet var resultTextView: UITextView!
    
    
    
    // MARK: User actions
    
    @IBAction func firstDemoAction() {
        
        // UI
        resultTextView.text = "Affichage simple des valeurs de l'observable :\n\n"
        
        // Rx
        _ = Observable.of("one", "two", "three", "four", "five")
            .subscribeOn(ConcurrentDispatchQueueScheduler(qos: .background))
            .observeOn(MainScheduler.instance)
            .subscribe(
                onNext: { (value) in
                    self.printUI(content: "onNext : \(value)")
            },
                onError: { (error) in
                    self.printUI(content: "onError")
            },
                onCompleted: {
                    self.printUI(content: "onComplete")
            })
    }
    
    
    
    @IBAction func secondDemoAction() {
        
        // UI
        resultTextView.text = "Edition et affichage des valeurs de l'observable :\n\n"
        
        // Rx
        _ = Observable.of("one", "two", "three", "four", "five")
            .map { "\($0) bananas" }
            .subscribeOn(ConcurrentDispatchQueueScheduler(qos: .background))
            .observeOn(MainScheduler.instance)
            .subscribe(
                onNext: { (value) in
                    self.printUI(content: "onNext : \(value)")
            },
                onError: { (error) in
                    self.printUI(content: "onError")
            },
                onCompleted: {
                    self.printUI(content: "onComplete")
            })
    }
    
    
    
    @IBAction func thirdDemoAction() {
        
        // UI
        resultTextView.text = "Filtre les valeurs de 3+ caractères puis édition des valeurs avec un interval d'une seconde :\n\n"
        
        // Rx
        let defaultObservable = Observable.of("one", "two", "three", "four", "five")
        let intervals = Observable<NSInteger>.interval(1.0, scheduler: MainScheduler.instance)
        
        _ = Observable
            .zip(defaultObservable, intervals) { return $0.0 }
            .filter { $0.characters.count > 3 }
            .map { "\($0) bananas" }
            .subscribeOn(ConcurrentDispatchQueueScheduler(qos: .background))
            .observeOn(MainScheduler.instance)
            .subscribe(
                onNext: { (value) in
                    self.printUI(content: value)
            },
                onError: { (error) in
                    self.printUI(content: "onError")
            },
                onCompleted: {
                    self.printUI(content: "End")
            })
    }
    
    
    
    // MARK: UI
    
    func printUI(content: String) {
        resultTextView.text = resultTextView.text + "[\(Date().description)] \(content)\n"
    }
}

