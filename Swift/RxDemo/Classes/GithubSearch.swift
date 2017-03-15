//
//  GithubSearch.swift
//  RxDemo
//
//  Created by Aurélien DELRUE on 03/01/2017.
//  Copyright © 2017 Aurélien DELRUE. All rights reserved.
//

import UIKit
import ObjectMapper


class GithubSearch: Mappable {
    
    var items: [GithubItem] = [GithubItem]()
    
    // Init
    required init?(map: Map) {}
    
    // Mappable
    func mapping(map: Map) {
        items    <- map["items"]
    }
}
