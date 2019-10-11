module.exports = function(grunt) {
    
    const sass = require('node-sass');
     
    require('load-grunt-tasks')(grunt);
     
    grunt.initConfig({
        sass: {
            options: {
                implementation: sass,
                sourceMap: true
            },
            dist: {
                files: {
                    'public/css/style.css': 'src/main.scss'
                }
            }
        },
        watch: {
            options: {
                livereload: true,
            },
            css:{
                files: ['src/*.scss'],
                tasks: ['sass']
            },
        }
    });

    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('default', ['watch']);

};
