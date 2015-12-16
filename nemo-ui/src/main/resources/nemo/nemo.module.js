/*
 * Copyright (c) 2015 Huawei, Inc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
define(['angularAMD', 'app/routingConfig', 'app/core/core.services','Restangular', 'common/config/env.module'], function(ng) {

  var nemo = angular.module('app.nemo', ['ui.router.state','app.core','restangular', 'config']);


  nemo.config(function($stateProvider, $controllerProvider, $compileProvider, $provide, $translateProvider, NavHelperProvider) {

  nemo.register = {
      controller : $controllerProvider.register,
      directive : $compileProvider.directive,
      service : $provide.service,
      factory : $provide.factory
    };

    $translateProvider.useStaticFilesLoader({
      prefix: 'assets/data/locale-',
      suffix: '.json'
    });

    NavHelperProvider.addControllerUrl('app/nemo/nemo.controller');
    NavHelperProvider.addToMenu('nemo', {
      "link": "#/nemo",
      "title": "Nemo",
      "active": "main.nemo",
      "icon": "icon-link",
      "page": {
        "title": "NEMO",
        "description": "NEMO"
      }
    });

    var access = routingConfig.accessLevels;
    $stateProvider.state('main.nemo', {
      url: 'nemo',
      access: access.public,
      views : {
        'content' : {
          templateUrl: 'src/app/nemo/register.html',
          controller: 'NemoController'
        }
      }
    });

  });

  return nemo;

});

