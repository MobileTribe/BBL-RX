//
//  NetworkViewController.swift
//  RxDemo
//
//  Created by Aurélien DELRUE on 29/12/2016.
//  Copyright © 2016 Aurélien DELRUE. All rights reserved.
//

import UIKit
import SwiftyJSON
import RxSwift


class NetworkViewController: UIViewController {
    
    /// UI
    @IBOutlet var resultTextView: UITextView!
    
    
    
    // MARK: User actions
    
    @IBAction func firstDemoAction() {
        
        // UI
        resultTextView.text = nil
        
        // Rx
        _ = Observable.just("rx")
            
            // Network request
            .flatMap { (_) -> Observable<GithubSearch> in
                return API.getRxSearch()
            }
            
            // Observable of github items
            .flatMap { (githubSearch) -> Observable<GithubItem> in
                return Observable.from(githubSearch.items)
            }
            
            // Take the 3 first github repo
            .take(3)
            
            // Get releases for each repo
            .flatMap { githubItem -> Observable<JSON> in
                self.printUI(content: "Repo : '\(githubItem.name)'")
                return API.getGithubReleasesList(releasesUrl: githubItem.releasesUrl)
            }
            
            // Get latest release informations
            .flatMap { githubReleases -> Observable<JSON> in
                print(githubReleases[0]["url"])
                return API.getReleaseInformations(releaseUrl: githubReleases[0]["url"].stringValue)
             }
            
            // Map only release tag
            .map {
                    " -> Last tag by '\($0["author"]["login"])' : \($0["tag_name"])"
            }
            
            .observeOn(MainScheduler.instance)
            .subscribeOn(ConcurrentDispatchQueueScheduler(qos: .background))
            .subscribe(
                onNext: { (value) in
                    self.printUI(content: value)
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
