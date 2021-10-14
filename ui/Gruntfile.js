module.exports = function(grunt) {

    const sass = require('sass');

    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        sass: {
            options: {
                implementation: sass,
                sourceMap: false
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
