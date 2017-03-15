//
//  GithubItem.swift
//  RxDemo
//
//  Created by Aurélien DELRUE on 03/01/2017.
//  Copyright © 2017 Aurélien DELRUE. All rights reserved.
//

import UIKit
import ObjectMapper


class GithubItem: Mappable {
    
    var name: String = ""
    
    private var releases_url: String = ""
    var releasesUrl: String {
        get {
                return releases_url.replacingOccurrences(of: "{/id}", with: "")
        }
    }
    
    
    // Init
    required init?(map: Map) {}
    
    // Mappable
    func mapping(map: Map) {
        name    <- map["name"]
        releases_url    <- map["releases_url"]
    }
}
