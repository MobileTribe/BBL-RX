//
//  API.swift
//  RxDemo
//
//  Created by Aurélien DELRUE on 02/01/2017.
//  Copyright © 2017 Aurélien DELRUE. All rights reserved.
//

import UIKit

import Alamofire

import ObjectMapper

import RxAlamofire
import RxSwift
import SwiftyJSON


// Github search url
let rxGithubSearchURL = URL(string: "https://api.github.com/search/repositories")!


class API: NSObject {
    
    /// Get repositories list
    class func getRxSearch() -> Observable<GithubSearch> {
        
        let params = ["q" : "rx", "sort" : "stars"]
        
        return
            request(.get, rxGithubSearchURL, parameters: params)
                
                .flatMap { (request) -> Observable<Any> in
                    return request.validate(statusCode: 200..<300).rx.json()
                }
                
                .flatMap { (jsonStr) -> Observable<GithubSearch> in
                    
                    guard let githubSearch = GithubSearch(JSON: jsonStr as! [String : AnyObject]) else {
                        return Observable.empty()
                    }
                    
                    return Observable.just(githubSearch)
        }
    }
    
    
    
    /// Get releases list
    class func getGithubReleasesList(releasesUrl: String) -> Observable<JSON> {
        
        guard let url = URL(string: releasesUrl) else {
            return Observable.empty()
        }
        
        return
            request(.get, url)
                .flatMap { (request) -> Observable<Any> in
                    return request.validate(statusCode: 200..<300).rx.json()
                }
                
                .flatMap { (jsonStr) -> Observable<JSON> in
                    return Observable.just(JSON(jsonStr))
        }
    }
    
    
    
    /// Get latest release informations
    class func getReleaseInformations(releaseUrl: String) -> Observable<JSON> {
        
        guard let url = URL(string: releaseUrl) else {
            return Observable.empty()
        }
        
        return
            request(.get, url)
                .flatMap { (request) -> Observable<Any> in
                    
                    return request
                        .validate(statusCode: 200..<300)
                        .rx.json()
                }
                
                .flatMap { (jsonStr) -> Observable<JSON> in
                    return Observable.just(JSON(jsonStr))
        }
    }
}
